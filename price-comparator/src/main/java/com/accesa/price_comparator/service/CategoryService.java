package com.accesa.price_comparator.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accesa.price_comparator.contracts.CategoryRepository;
import com.accesa.price_comparator.model.Category;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> get() {
        return this.categoryRepository.findAll();
    }

    public Category getById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    public Category update(Long id, Category updatedCtegory) {
        Category category = getById(id);
        category.setName(updatedCtegory.getName());
        return this.categoryRepository.save(category);
    }

    public void delete(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
