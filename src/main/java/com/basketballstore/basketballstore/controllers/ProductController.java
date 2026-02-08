package com.basketballstore.basketballstore.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.basketballstore.basketballstore.models.Product;
import com.basketballstore.basketballstore.repositories.ProductRepository;
import java.util.List;

@RestController
@RequestMapping("/products") // base route: localhost:8080/products
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public List<Product> listAll() {
        return repository.findAll();
    }
    
    @PostMapping
    public Product create(@RequestBody Product product) {
        return repository.save(product);
    }
}

