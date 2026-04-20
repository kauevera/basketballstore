package com.basketballstore.basketballstore.dto;

import jakarta.validation.constraints.NotNull;

public record OrderCreationDTO (
    @NotNull(message = "this field is required.")
    Long productId,
    @NotNull(message = "this field is required.")
    Long userId,
    @NotNull(message = "this field is required.")
    Long paymentMethodId
){

}
