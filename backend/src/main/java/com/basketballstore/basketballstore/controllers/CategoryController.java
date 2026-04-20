package com.basketballstore.basketballstore.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

// importing layers
import com.basketballstore.basketballstore.dto.CategoryCreationDTO;
import com.basketballstore.basketballstore.repositories.CategoryRepository;
import com.basketballstore.basketballstore.services.CategoryService;
import com.basketballstore.basketballstore.models.Category;

@RestController
@RequestMapping("/categories") // base route: localhost:8080/categories
public class CategoryController {
    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryService service;

    @GetMapping
    public List<Category> listAll() {
        return repository.findAll();
    }
    
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid CategoryCreationDTO data) {
        try {
            service.createCategory(data);
            return ResponseEntity.status(201).body("category successfully created at the basketball store");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // delete a category
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("category successfully deleted from the basketball store");
        } else {
            return ResponseEntity.status(404).body("category not found");
        }
    }
}