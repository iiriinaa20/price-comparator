package com.accesa.price_comparator.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accesa.price_comparator.contracts.CategoryRepository;
import com.accesa.price_comparator.contracts.ProductRepository;
import com.accesa.price_comparator.model.Product;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> get() {
        return this.productRepository.findAll();
    }

    public Product getById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Product save(Product product) {
        categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Invalid category"));
        return productRepository.save(product);
    }

    public Product update(Long id, Product updated) {
        Product existing = getById(id);
        existing.setName(updated.getName());
        existing.setCategory(updated.getCategory());
        return productRepository.save(existing);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
