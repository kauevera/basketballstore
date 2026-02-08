package com.basketballstore.basketballstore.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users_table")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private Long user_attribute_id;

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
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Long getUser_attribute_id() {
        return user_attribute_id;
    }
    public void setUser_attribute_id(Long user_attribute_id) {
        this.user_attribute_id = user_attribute_id;
    }    
}