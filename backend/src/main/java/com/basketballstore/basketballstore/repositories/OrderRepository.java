package com.basketballstore.basketballstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basketballstore.basketballstore.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
