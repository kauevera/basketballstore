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
    private UserRepository user_repository;
    @Autowired
    private ProductRepository product_repository;
    @Autowired
    private TransactionRepository transaction_repository;
    @Autowired
    private PaymentMethodRepository payment_method_repository;


    public Order createOrder(OrderCreationDTO dto) {
        // checking if the user_id exists
        if (!user_repository.findById(dto.user_id()).isPresent()){
            throw new RuntimeException("invalid user_id");
        }

        Product product = product_repository.findById(dto.product_id()).orElseThrow(() -> new RuntimeException("invalid product"));

        // checking if the product is in stock
        if ((product.getQuantity() < 1) || (product.getAvailability() == false)) {
            throw new RuntimeException("this product is not available");
        }

        // checking the paymentMethodId
        if (!payment_method_repository.findById(dto.paymentMethodId()).isPresent()) {
            throw new RuntimeException("this payment method is not accepted");
        }

        Order newOrder = new Order();
        newOrder.setUser_id(dto.user_id());
        newOrder.setProduct_id(dto.product_id());
        newOrder.setpaymentMethodId(dto.paymentMethodId());
        repository.save(newOrder);
   
        Transaction newTransaction = new Transaction();
        newTransaction.setOrder_id(newOrder.getId());
        newTransaction.setpaymentMethodId(newOrder.getpaymentMethodId());
        transaction_repository.save(newTransaction);

        newOrder.setTransaction_id(newTransaction.getId());

        return repository.save(newOrder);
    }
}
