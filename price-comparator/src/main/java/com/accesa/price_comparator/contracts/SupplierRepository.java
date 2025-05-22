package com.accesa.price_comparator.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accesa.price_comparator.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
