package com.basketballstore.basketballstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserLoginDTO (
    @Email String email,
    @Size(min = 8, message = "the password must be at least 8 characters long.") String password
){

}
