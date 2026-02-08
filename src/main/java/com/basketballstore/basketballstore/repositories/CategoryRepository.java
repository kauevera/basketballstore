package com.basketballstore.basketballstore.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.basketballstore.basketballstore.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);     
}
