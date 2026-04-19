package com.basketballstore.basketballstore.dto;

public record ProductUpdateDTO (
    String name,
    Double price,
    Boolean availability,
    Double quantity,
    Long categoryId,
    Long brandId
){  
    // checking if the dto is empty
    public boolean isEmpty() {
        return name == null && price == null && availability == null && 
               quantity == null && categoryId == null && brandId == null;
    }
}