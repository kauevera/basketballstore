package com.basketballstore.basketballstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderCreationDTO(
    @NotNull(message = "this field is required.")
    Long productId,
    @NotNull(message = "this field is required.")
    Long userId,
    @NotNull(message = "this field is required.")
    Long paymentMethodId,
    @NotNull(message = "this field is required.")
    @Min(value = 1, message = "quantity must be at least 1")
    Integer quantity
) {}
