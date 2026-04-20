package com.basketballstore.basketballstore.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

// importing layers
import com.basketballstore.basketballstore.dto.UserRegistrationDTO;
import com.basketballstore.basketballstore.dto.UserLoginDTO;
import com.basketballstore.basketballstore.models.User;
import com.basketballstore.basketballstore.repositories.UserRepository;
import com.basketballstore.basketballstore.services.UserService;

@RestController
@RequestMapping("/users") // base route: localhost:8080/users
public class UserController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService service;

    @GetMapping
    public List<User> listAll() {
        return repository.findAll();
    }
    
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserRegistrationDTO data) {
        try {
            service.registerUser(data);
            return ResponseEntity.status(201).body("user successfully registered at the basketball store");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDTO data) {
        try {
            service.loginUser(data);
            return ResponseEntity.status(201).body("login successful.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // delete an user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("user successfully deleted from the basketball store");
        } else {
            return ResponseEntity.status(404).body("user not found");
        }
    }
}

