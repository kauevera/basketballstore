package com.basketballstore.basketballstore.models;

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
    @Column(nullable = false)
    private Long category_id;
    @Column(nullable = false)
    private Long brand_id;

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
    public Long getCategory_id() {
        return category_id;
    }
    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
    public Long getBrand_id() {
        return brand_id;
    }
    public void setBrand_id(Long brand_id) {
        this.brand_id = brand_id;
    }
}