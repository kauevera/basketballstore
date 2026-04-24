package com.basketballstore.basketballstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basketballstore.basketballstore.models.PaymentMethod;
import com.basketballstore.basketballstore.repositories.PaymentMethodRepository;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodRepository repository;

    @GetMapping
    public List<PaymentMethod> listAll() {
        return repository.findAll();
    }
}
