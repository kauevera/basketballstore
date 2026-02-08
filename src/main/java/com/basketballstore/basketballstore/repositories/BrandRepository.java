package com.basketballstore.basketballstore.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.basketballstore.basketballstore.models.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    
}
