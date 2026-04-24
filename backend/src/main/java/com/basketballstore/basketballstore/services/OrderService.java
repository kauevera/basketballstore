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

        if (!paymentMethodRepository.findById(dto.paymentMethodId()).isPresent()) {
            throw new RuntimeException("this payment method is not accepted");
        }

        Order newOrder = new Order();
        newOrder.setuserId(dto.userId());
        newOrder.setproductId(dto.productId());
        newOrder.setpaymentMethodId(dto.paymentMethodId());
        repository.save(newOrder);

        Transaction newTransaction = new Transaction();
        newTransaction.setorderId(newOrder.getId());
        newTransaction.setpaymentMethodId(newOrder.getpaymentMethodId());
        transactionRepository.save(newTransaction);

        newOrder.settransactionId(newTransaction.getId());
        return repository.save(newOrder);
    }

    public List<OrderResponseDTO> getUserOrders(Long userId) {
        return repository.findByUserId(userId).stream().map(order -> {
            String productName = productRepository.findById(order.getproductId())
                    .map(Product::getName).orElse("Produto desconhecido");
            Double productPrice = productRepository.findById(order.getproductId())
                    .map(Product::getPrice).orElse(0.0);
            String paymentMethodTitle = paymentMethodRepository.findById(order.getpaymentMethodId())
                    .map(PaymentMethod::getTitle).orElse("Desconhecido");
            String formattedDate = order.getcreationDate() != null
                    ? order.getcreationDate().format(DATE_FORMATTER) : "";

            return new OrderResponseDTO(
                    order.getId(),
                    order.getState(),
                    formattedDate,
                    productName,
                    productPrice,
                    paymentMethodTitle
            );
        }).toList();
    }

    public Order payOrder(Long orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("order not found"));

        if (!order.getState().equals("awaiting_payment")) {
            throw new RuntimeException("order cannot be paid in current state");
        }

        order.setState("payment_ok");
        repository.save(order);

        transactionRepository.findByOrderId(orderId).ifPresent(transaction -> {
            transaction.setState(true);
            transactionRepository.save(transaction);
        });

        return order;
    }
}
