package com.accesa.price_comparator.contracts;

import com.accesa.price_comparator.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;
import com.accesa.price_comparator.model.SupplierProduct;
import com.accesa.price_comparator.model.Category;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Long> {
    List<SupplierProduct> findByDiscountNotNullOrderByDiscountDesc();

    List<SupplierProduct> findByStartAfter(LocalDate date);

    List<SupplierProduct> findByProductIdAndSupplierIdOrderByIdAsc(Long productId, Long supplierId);

    List<SupplierProduct> findByProductIdAndProductCategoryOrderByIdAsc(Long productId, Category category);

    List<SupplierProduct> findByProduct(Product product);

    List<SupplierProduct> findByProductId(Long productId);

    //List<SupplierProduct> findByProductId(String productId);
}
