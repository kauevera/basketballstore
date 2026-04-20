package com.basketballstore.basketballstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// importing layers
import com.basketballstore.basketballstore.dto.ProductCreationDTO;
import com.basketballstore.basketballstore.dto.ProductUpdateDTO;
import com.basketballstore.basketballstore.models.Product;
import com.basketballstore.basketballstore.repositories.BrandRepository;
import com.basketballstore.basketballstore.repositories.CategoryRepository;
import com.basketballstore.basketballstore.repositories.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    
    public Product createProduct(ProductCreationDTO dto) {
        // checking if the brand exists
        if (!brandRepository.findById(dto.brandId()).isPresent()) {
            throw new RuntimeException("please provide a valid brand");
        }

        // checking if the category exists
        if (!categoryRepository.findById(dto.categoryId()).isPresent()) {
            throw new RuntimeException("please provide a valid category");
        }

        Product newProduct = new Product();
        newProduct.setName(dto.name());
        newProduct.setPrice(dto.price());
        newProduct.setAvailability(dto.availability());
        newProduct.setQuantity(dto.quantity());
        newProduct.setcategoryId(dto.categoryId());
        newProduct.setbrandId(dto.brandId());
 
        return repository.save(newProduct);
    }

    public Product updateProduct(Long id, ProductUpdateDTO dto) {
        // checking if the id exists in products_table
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException("please provide a valid product"));

        // checking if the dto is empty
        if (dto.isEmpty()) {
            throw new RuntimeException("no changes were provided");
        }

        if (dto.name() != null) product.setName(dto.name());
        if (dto.price() != null) product.setPrice(dto.price());
        if (dto.availability() != null) product.setAvailability(dto.availability());
        if (dto.quantity() != null) product.setQuantity(dto.quantity());
        if (dto.categoryId() != null) product.setcategoryId(dto.categoryId());
        if (dto.brandId() != null) product.setbrandId(dto.brandId());

        return repository.save(product);
    }
}
