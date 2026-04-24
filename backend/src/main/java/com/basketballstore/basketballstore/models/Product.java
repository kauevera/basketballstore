package com.basketballstore.basketballstore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products_table")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private Double price;
    private Boolean availability = true;
    @Column(nullable = false)
    private Double quantity;
    @JsonProperty("categoryId")
    @Column(nullable = false)
    private Long categoryId;
    @Column(nullable = false)
    private Long brandId;

    //getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Boolean getAvailability() {
        return availability;
    }
    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
    public Double getQuantity() {
        return quantity;
    }
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    public Long getcategoryId() {
        return categoryId;
    }
    public void setcategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public Long getbrandId() {
        return brandId;
    }
    public void setbrandId(Long brandId) {
        this.brandId = brandId;
    }
}