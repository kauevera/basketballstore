package com.basketballstore.basketballstore.dto;

public record OrderResponseDTO(
    Long id,
    String state,
    String creationDate,
    String productName,
    Double productPrice,
    String paymentMethodTitle
) {}
