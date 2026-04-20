package com.basketballstore.basketballstore.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

// importing layers
import com.basketballstore.basketballstore.dto.BrandCreationDTO;
import com.basketballstore.basketballstore.repositories.BrandRepository;
import com.basketballstore.basketballstore.services.BrandService;
import com.basketballstore.basketballstore.models.Brand;

@RestController
@RequestMapping("/brands") // base route: localhost:8080/brands
public class BrandController {
    @Autowired
    private BrandRepository repository;

    @Autowired
    private BrandService service;

    @GetMapping
    public List<Brand> listAll() {
        return repository.findAll();
    }
    
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid BrandCreationDTO data) {
        try {
            service.createBrand(data);
            return ResponseEntity.status(201).body("brand successfully created at the basketball store");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // delete a brand
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("brand successfully deleted from the basketball store");
        } else {
            return ResponseEntity.status(404).body("brand not found");
        }
    }
}