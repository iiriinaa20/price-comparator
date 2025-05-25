package com.accesa.price_comparator.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accesa.price_comparator.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameIgnoreCase(String name);
}
