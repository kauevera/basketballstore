package com.basketballstore.basketballstore.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions_table")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private LocalDateTime creationDate = LocalDateTime.now();
    @Column(nullable = false)
    private Long paymentMethodId;
    private Boolean state = false;
    @Column(nullable = false)
    private Long orderId;

    //getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getcreationDate() {
        return creationDate;
    }
    public void setcreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public Long getpaymentMethodId() {
        return paymentMethodId;
    }
    public void setpaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    public Boolean getState() {
        return state;
    }
    public void setState(Boolean state) {
        this.state = state;
    }
    public Long getorderId() {
        return orderId;
    }
    public void setorderId(Long orderId) {
        this.orderId = orderId;
    }
}