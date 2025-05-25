package com.accesa.price_comparator.service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.accesa.price_comparator.contracts.SupplierProductRepository;
import com.accesa.price_comparator.model.SupplierProduct;

@Service
public class ProductHistoryService {

    private final SupplierProductRepository repository;

    public ProductHistoryService(SupplierProductRepository repository) {
        this.repository = repository;
    }

    public Map<String, Map<String, Map<String, Double>>> getProductsHistoryFiltered(
            Long productId,
            Long supplierId,
            String category,
            String brand) {
        Predicate<SupplierProduct> filter = sp -> {
            boolean productMatches = productId == null || sp.getProduct().getId() == productId;
            boolean supplierMatches = supplierId == null || sp.getSupplier().getId() == supplierId;
            boolean categoryMatches = category == null
                    || category.equalsIgnoreCase(sp.getProduct().getCategory().getName());
            // boolean brandMatches = brand == null ||
            // sp.getProduct().getBrand().equals(brand);
            return productMatches && supplierMatches && categoryMatches;
        };

        Map<String, Map<String, Map<String, Double>>> history = new TreeMap<>();

        repository.findAll().stream()
                .filter(filter)
                .forEach(sp -> {
                    String supplierName = sp.getSupplier().getName();
                    String date = (sp.getStart() != null)
                            ? sp.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                            : "unknown-date";
                    double basePrice = sp.getBasePrice();
                    Double discountPrice = sp.getDiscountPrice();
                    double discountedPrice = (discountPrice != null) ? discountPrice : sp.getBasePrice();

                    Map<String, Double> priceData = new HashMap<>();
                    priceData.put("price", basePrice);
                    priceData.put("discounted_price", discountedPrice);

                    history
                            .computeIfAbsent(supplierName, k -> new TreeMap<>())
                            .put(date, priceData);
                });

        return history;
    }
}
