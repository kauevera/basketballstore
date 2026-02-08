package com.basketballstore.basketballstore.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.basketballstore.basketballstore.models.UserAttribute;

public interface UserAttributeRepository extends JpaRepository<UserAttribute, Long> {
    
}
