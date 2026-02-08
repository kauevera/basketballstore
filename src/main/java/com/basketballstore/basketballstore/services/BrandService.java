package com.basketballstore.basketballstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// importing layers
import com.basketballstore.basketballstore.dto.BrandCreationDTO;
import com.basketballstore.basketballstore.models.Brand;
import com.basketballstore.basketballstore.repositories.BrandRepository;

@Service
public class BrandService {
    @Autowired
    private BrandRepository repository;    
    
    public Brand createBrand(BrandCreationDTO dto) {
        // checking if this title exists
        if (repository.findByTitle(dto.title()).isPresent()) {
            throw new RuntimeException("this brand already exists");
        }

        Brand newBrand = new Brand();
        newBrand.setTitle(dto.title());
 
        return repository.save(newBrand);
    }
}
