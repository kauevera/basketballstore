package com.basketballstore.basketballstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO (
    @Email @NotBlank String email,
    @Size(min = 8, message = "the password must be at least 8 characters long.") @NotBlank String password
){

}
