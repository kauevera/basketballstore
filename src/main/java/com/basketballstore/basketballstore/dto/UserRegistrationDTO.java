package com.basketballstore.basketballstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegistrationDTO (
    @Email String email,
    @Size(min = 8, message = "the password must be at least 8 characters long.") String password,
    String confirmPassword,
    @NotBlank(message = "this field is required.")
    String name,
    @NotNull(message = "this field is required.")
    Integer age,
    @NotBlank(message = "this field is required.")
    String gender,
    @NotBlank(message = "this field is required.")
    String country,
    @NotBlank(message = "this field is required.")
    String city,
    @NotBlank(message = "this field is required.")
    String zipCode
){

}
