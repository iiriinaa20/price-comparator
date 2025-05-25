package com.accesa.price_comparator.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accesa.price_comparator.model.Product;
import com.accesa.price_comparator.service.ProductHistoryService;
import com.accesa.price_comparator.service.ProductService;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService productService;
    private final ProductHistoryService productHistoryService;

    public ProductController(ProductService productService, ProductHistoryService priceHistoryService) {
        this.productService = productService;
        this.productHistoryService = priceHistoryService;

    }

    @GetMapping
    public List<Product> getAll() {
        return this.productService.get();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return this.productService.getById(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return this.productService.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return this.productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.productService.delete(id);
    }

    @GetMapping("/price-history/trend")
    public Map<String, Map<String, Map<String, Double>>> getTrend(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long supplierId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand) {
        return productHistoryService.getProductsHistoryFiltered(productId, supplierId, category, brand);
    }

}
