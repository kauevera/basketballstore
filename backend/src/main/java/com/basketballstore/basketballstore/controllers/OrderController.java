package com.basketballstore.basketballstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

// importing layers
import com.basketballstore.basketballstore.models.Order;
import com.basketballstore.basketballstore.repositories.OrderRepository;
import com.basketballstore.basketballstore.services.OrderService;
import com.basketballstore.basketballstore.dto.OrderCreationDTO;

@RestController
@RequestMapping("/orders") // base route: localhost:8080/orders
public class OrderController {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderService service;

    @GetMapping
    public List<Order> listAll() {
        return repository.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid OrderCreationDTO data) {
        try {
            service.createOrder(data);
            return ResponseEntity.status(201).body("order placed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
