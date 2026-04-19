package com.basketballstore.basketballstore.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "payments_methods_table")
public class PaymentMethod implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String title;

    //getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String name) {
        this.title = name;
    } 
}
