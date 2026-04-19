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
    private LocalDateTime creation_date = LocalDateTime.now();
    @Column(nullable = false)
    private Long paymentMethodId;
    private Boolean state = false;
    @Column(nullable = false)
    private Long order_id;

    //getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getCreation_date() {
        return creation_date;
    }
    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
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
    public Long getOrder_id() {
        return order_id;
    }
    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }
}