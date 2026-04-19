package com.basketballstore.basketballstore.dto;

import jakarta.validation.constraints.NotNull;

public record TransactionCreationDTO (
    @NotNull(message = "this field is required.")
    Long orderId,
    @NotNull(message = "this field is required.")
    Long paymentMethodId,
    @NotNull(message = "this field is required.")
    String state
){

}
