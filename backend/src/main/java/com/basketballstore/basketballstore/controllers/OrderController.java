package com.basketballstore.basketballstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.basketballstore.basketballstore.dto.OrderCreationDTO;
import com.basketballstore.basketballstore.dto.OrderResponseDTO;
import com.basketballstore.basketballstore.models.Order;
import com.basketballstore.basketballstore.repositories.OrderRepository;
import com.basketballstore.basketballstore.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderService service;

    @GetMapping
    public List<Order> listAll() {
        return repository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponseDTO> listByUser(@PathVariable Long userId) {
        return service.getUserOrders(userId);
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<Object> pay(@PathVariable Long id) {
        try {
            service.payOrder(id);
            return ResponseEntity.ok("payment confirmed");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Object> cancel(@PathVariable Long id) {
        try {
            service.cancelOrder(id);
            return ResponseEntity.ok("order cancelled");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
