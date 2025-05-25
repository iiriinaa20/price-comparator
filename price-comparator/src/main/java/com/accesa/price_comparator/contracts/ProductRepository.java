package com.accesa.price_comparator.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accesa.price_comparator.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
