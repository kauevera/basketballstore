package com.basketballstore.basketballstore.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

// importing layers
import com.basketballstore.basketballstore.dto.ProductCreationDTO;
import com.basketballstore.basketballstore.dto.ProductUpdateDTO;
import com.basketballstore.basketballstore.models.Product;
import com.basketballstore.basketballstore.repositories.ProductRepository;
import com.basketballstore.basketballstore.services.ProductService;

@RestController
@RequestMapping("/products") // base route: localhost:8080/products
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> listAll() {
        return repository.findAll();
    }
    
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid ProductCreationDTO data) {
        try {
            service.createProduct(data);
            return ResponseEntity.status(201).body("product successfully created at the basketball store");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ProductUpdateDTO dto) {
        if (repository.existsById(id)) {
            Product updatedProduct = service.updateProduct(id, dto);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.status(404).body("product not found");
        }
    }

    // delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("product successfully deleted from the basketball store");
        } else {
            return ResponseEntity.status(404).body("product not found");
        }
    }
}

