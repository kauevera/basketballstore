package com.basketballstore.basketballstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductCreationDTO (
    @NotBlank(message = "this field is required.")
    String name,
    @NotNull(message = "this field is required.")
    Double price,
    @NotBlank(message = "this field is required.")
    Boolean availability,
    @NotNull(message = "this field is required.")
    Double quantity,
    @NotNull(message = "this field is required.")
    Long category_id,
    @NotNull(message = "this field is required.")
    Long brand_id
){

}