package com.basketballstore.basketballstore.dto;

public record ProductUpdateDTO (
    String name,
    Double price,
    Boolean availability,
    Double quantity,
    Long category_id,
    Long brand_id
){  
    // checking if the dto is empty
    public boolean isEmpty() {
        return name == null && price == null && availability == null && 
               quantity == null && category_id == null && brand_id == null;
    }
}