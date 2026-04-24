package com.basketballstore.basketballstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.basketballstore.basketballstore.dto.LoginResponseDTO;
import com.basketballstore.basketballstore.dto.UserLoginDTO;
import com.basketballstore.basketballstore.dto.UserRegistrationDTO;
import com.basketballstore.basketballstore.models.User;
import com.basketballstore.basketballstore.repositories.UserRepository;
import com.basketballstore.basketballstore.utils.JwtUtil;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User registerUser(UserRegistrationDTO dto) {
        if (!dto.password().equals(dto.confirmPassword())) {
            throw new RuntimeException("the passwords don't match");
        }

        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("email already registered");
        }

        String encryptedPassword = passwordEncoder.encode(dto.password());

        User newUser = new User();
        newUser.setEmail(dto.email());
        newUser.setPassword(encryptedPassword);
        newUser.setName(dto.name());
        newUser.setAge(dto.age());
        newUser.setGender(dto.gender());

        return repository.save(newUser);
    }

    public LoginResponseDTO loginUser(UserLoginDTO dto) {
        User user = repository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("invalid credentials"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponseDTO(token, user.getId(), user.getEmail());
    }
}
