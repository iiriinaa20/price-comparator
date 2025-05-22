package com.accesa.price_comparator.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accesa.price_comparator.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
