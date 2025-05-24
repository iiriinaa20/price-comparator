package com.accesa.price_comparator.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;
import com.accesa.price_comparator.model.SupplierProduct;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Long> {
    List<SupplierProduct> findByDiscountNotNullOrderByDiscountDesc();
    List<SupplierProduct> findByStartAfter(LocalDate date);
    List<SupplierProduct> findByProductIdAndSupplierIdOrderByIdAsc(Long productId, Long supplierId);
    List<SupplierProduct> findByUnit(String unit);
}

