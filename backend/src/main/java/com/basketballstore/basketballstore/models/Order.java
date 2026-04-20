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
    private LocalDateTime creationDate = LocalDateTime.now();;
    private LocalDateTime estimatedDeliveryDate = null;
    private LocalDateTime realDeliveryDate = null;
    @Column(nullable = false, updatable = false)
    private Long userId;
    @Column(nullable = false, updatable = false)
    private Long productId;
    private Long transactionId = null;
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
    public LocalDateTime getcreationDate() {
        return creationDate;
    }
    public void setcreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public LocalDateTime getestimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }
    public void setestimatedDeliveryDate(LocalDateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }
    public LocalDateTime getrealDeliveryDate() {
        return realDeliveryDate;
    }
    public void setrealDeliveryDate(LocalDateTime realDeliveryDate) {
        this.realDeliveryDate = realDeliveryDate;
    }
    public Long getuserId() {
        return userId;
    }
    public void setuserId(Long userId) {
        this.userId = userId;
    }
    public Long getproductId() {
        return productId;
    }
    public void setproductId(Long productId) {
        this.productId = productId;
    }
    public Long gettransactionId() {
        return transactionId;
    }
    public void settransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
    public Long getpaymentMethodId() {
        return paymentMethodId;
    }
    public void setpaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}
