package com.basketballstore.basketballstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// importing layers
import com.basketballstore.basketballstore.dto.CategoryCreationDTO;
import com.basketballstore.basketballstore.models.Category;
import com.basketballstore.basketballstore.repositories.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;    
    
    public Category createCategory(CategoryCreationDTO dto) {
        // checking if this title exists
        if (repository.findByTitle(dto.title()).isPresent()) {
            throw new RuntimeException("this category already exists");
        }

        Category newCategory = new Category();
        newCategory.setTitle(dto.title());
 
        return repository.save(newCategory);
    }
}
