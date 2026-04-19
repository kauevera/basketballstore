package com.basketballstore.basketballstore.services;

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


    public Order createOrder(OrderCreationDTO dto) {
        // checking if the userId exists
        if (!userRepository.findById(dto.userId()).isPresent()){
            throw new RuntimeException("invalid userId");
        }

        Product product = productRepository.findById(dto.productId()).orElseThrow(() -> new RuntimeException("invalid product"));

        // checking if the product is in stock
        if ((product.getQuantity() < 1) || (product.getAvailability() == false)) {
            throw new RuntimeException("this product is not available");
        }

        // checking the paymentMethodId
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
}
