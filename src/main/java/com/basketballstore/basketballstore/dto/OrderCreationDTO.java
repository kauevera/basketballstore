package com.basketballstore.basketballstore.dto;

import jakarta.validation.constraints.NotNull;

public record OrderCreationDTO (
    @NotNull(message = "this field is required.")
    Long product_id,
    @NotNull(message = "this field is required.")
    Long user_id,
    @NotNull(message = "this field is required.")
    Long paymentMethodId
){

}
