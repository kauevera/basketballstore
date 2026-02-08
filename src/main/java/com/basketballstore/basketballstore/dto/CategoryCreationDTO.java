package com.basketballstore.basketballstore.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreationDTO (
    @NotBlank(message = "this field is required.")
    String title
){

}