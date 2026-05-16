package com.basketballstore.basketballstore.services;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.basketballstore.basketballstore.dto.OrderResponseDTO;
import com.basketballstore.basketballstore.models.PaymentMethod;
import com.basketballstore.basketballstore.repositories.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basketballstore.basketballstore.dto.OrderCreationDTO;
import com.basketballstore.basketballstore.models.Order;
import com.basketballstore.basketballstore.models.Product;
import com.basketballstore.basketballstore.models.Transaction;
import com.basketballstore.basketballstore.repositories.OrderRepository;
import com.basketballstore.basketballstore.repositories.ProductRepository;
import com.basketballstore.basketballstore.repositories.UserRepository;
import com.basketballstore.basketballstore.repositories.TransactionRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Order createOrder(OrderCreationDTO dto) {
        if (!userRepository.findById(dto.userId()).isPresent()) {
            throw new RuntimeException("invalid userId");
        }

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("invalid product"));

        if ((product.getQuantity() < 1) || (product.getAvailability() == false)) {
            throw new RuntimeException("this product is not available");
        }

        if (dto.quantity() > product.getQuantity().intValue()) {
            throw new RuntimeException("insufficient stock for the requested quantity");
        }

        if (!paymentMethodRepository.findById(dto.paymentMethodId()).isPresent()) {
            throw new RuntimeException("this payment method is not accepted");
        }

        double newQuantity = product.getQuantity() - dto.quantity();
        product.setQuantity(newQuantity);
        if (newQuantity < 1) {
            product.setAvailability(false);
        }
        productRepository.save(product);

        Order newOrder = new Order();
        newOrder.setUserId(dto.userId());
        newOrder.setProductId(dto.productId());
        newOrder.setPaymentMethodId(dto.paymentMethodId());
        newOrder.setQuantity(dto.quantity());
        repository.save(newOrder);

        Transaction newTransaction = new Transaction();
        newTransaction.setOrderId(newOrder.getId());
        newTransaction.setPaymentMethodId(newOrder.getPaymentMethodId());
        transactionRepository.save(newTransaction);

        newOrder.setTransactionId(newTransaction.getId());
        return repository.save(newOrder);
    }

    public List<OrderResponseDTO> getUserOrders(Long userId) {
        return repository.findByUserId(userId).stream().map(order -> {
            String productName = productRepository.findById(order.getProductId())
                    .map(Product::getName).orElse("Produto desconhecido");
            Double productPrice = productRepository.findById(order.getProductId())
                    .map(Product::getPrice).orElse(0.0);
            String paymentMethodTitle = paymentMethodRepository.findById(order.getPaymentMethodId())
                    .map(PaymentMethod::getTitle).orElse("Desconhecido");
            String formattedDate = order.getCreationDate() != null
                    ? order.getCreationDate().format(DATE_FORMATTER) : "";

            return new OrderResponseDTO(
                    order.getId(),
                    order.getState(),
                    formattedDate,
                    productName,
                    productPrice,
                    paymentMethodTitle,
                    order.getQuantity() != null ? order.getQuantity() : 1
            );
        }).toList();
    }

    public Order payOrder(Long orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("order not found"));

        order.setState("payment_ok");
        repository.save(order);

        transactionRepository.findByOrderId(orderId).ifPresent(transaction -> {
            transaction.setState(true);
            transactionRepository.save(transaction);
        });

        return order;
    }

    public Order cancelOrder(Long orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("order not found"));

        order.setState("canceled");
        return repository.save(order);
    }
}
