package com.basketballstore.basketballstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// importing layers
import com.basketballstore.basketballstore.dto.UserRegistrationDTO;
import com.basketballstore.basketballstore.dto.UserLoginDTO;
import com.basketballstore.basketballstore.models.User;
import com.basketballstore.basketballstore.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationDTO dto) {
        // checking if the passwords matches
        if (!dto.password().equals(dto.confirm_password())) {
            throw new RuntimeException("the passwords don't match");
        }

        // checking if the email is unique
        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("email already registered");
        }

        String encryptedPassword = passwordEncoder.encode(dto.password());

        // creating the user register
        User newUser = new User();
        newUser.setEmail(dto.email());
        newUser.setPassword(encryptedPassword);
        newUser.setName(dto.name());

        return repository.save(newUser);
    }

    public User loginUser(UserLoginDTO dto) {
        // checking if the email exists
        if (!repository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("email not found");
        }

        User user = repository.findByEmail(dto.email()).orElseThrow(() -> new RuntimeException("invalid credentials"));

        // checking if is the password correct
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("invalid password");
        }
        
        return user;
    }
}
