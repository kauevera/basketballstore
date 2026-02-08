package com.basketballstore.basketballstore.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.basketballstore.basketballstore.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
