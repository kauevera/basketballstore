package com.basketballstore.basketballstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (!brandRepository.findById(dto.brandId()).isPresent()) {
            throw new RuntimeException("please provide a valid brand");
        }

        if (!categoryRepository.findById(dto.categoryId()).isPresent()) {
            throw new RuntimeException("please provide a valid category");
        }

        Product newProduct = new Product();
        newProduct.setName(dto.name());
        newProduct.setPrice(dto.price());
        newProduct.setAvailability(dto.availability());
        newProduct.setQuantity(dto.quantity());
        newProduct.setCategoryId(dto.categoryId());
        newProduct.setBrandId(dto.brandId());

        return repository.save(newProduct);
    }

    public Product updateProduct(Long id, ProductUpdateDTO dto) {
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException("please provide a valid product"));

        if (dto.isEmpty()) {
            throw new RuntimeException("no changes were provided");
        }

        if (dto.name() != null) product.setName(dto.name());
        if (dto.price() != null) product.setPrice(dto.price());
        if (dto.availability() != null) product.setAvailability(dto.availability());
        if (dto.quantity() != null) product.setQuantity(dto.quantity());
        if (dto.categoryId() != null) product.setCategoryId(dto.categoryId());
        if (dto.brandId() != null) product.setBrandId(dto.brandId());

        return repository.save(product);
    }
}
