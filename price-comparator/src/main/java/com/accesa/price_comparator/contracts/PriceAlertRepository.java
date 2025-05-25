package com.accesa.price_comparator.contracts;

import com.accesa.price_comparator.model.PriceAlert;
import com.accesa.price_comparator.model.User;
import com.accesa.price_comparator.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {
    List<PriceAlert> findByUser(User user);
    List<PriceAlert> findByProductAndTargetPriceGreaterThanEqual(Product product, Double currentPrice);
}