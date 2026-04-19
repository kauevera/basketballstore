package com.basketballstore.basketballstore.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders_table")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String state = "awaiting payment";
    @Column(updatable = false)
    private LocalDateTime creation_date = LocalDateTime.now();;
    private LocalDateTime estimated_delivery_date = null;
    private LocalDateTime real_delivery_date = null;
    @Column(nullable = false, updatable = false)
    private Long user_id;
    @Column(nullable = false, updatable = false)
    private Long product_id;
    private Long transaction_id = null;
    @Column(nullable = false)
    private Long paymentMethodId;

    //getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public LocalDateTime getCreation_date() {
        return creation_date;
    }
    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }
    public LocalDateTime getEstimated_delivery_date() {
        return estimated_delivery_date;
    }
    public void setEstimated_delivery_date(LocalDateTime estimated_delivery_date) {
        this.estimated_delivery_date = estimated_delivery_date;
    }
    public LocalDateTime getReal_delivery_date() {
        return real_delivery_date;
    }
    public void setReal_delivery_date(LocalDateTime real_delivery_date) {
        this.real_delivery_date = real_delivery_date;
    }
    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    public Long getProduct_id() {
        return product_id;
    }
    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }
    public Long getTransaction_id() {
        return transaction_id;
    }
    public void setTransaction_id(Long transaction_id) {
        this.transaction_id = transaction_id;
    }
    public Long getpaymentMethodId() {
        return paymentMethodId;
    }
    public void setpaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}
