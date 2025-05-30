package com.accesa.price_comparator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.accesa.price_comparator.dto.ProductHistoryDto;
import com.accesa.price_comparator.model.SupplierProduct;
import com.accesa.price_comparator.service.SupplierProductService;
import com.accesa.price_comparator.model.Category;
import com.accesa.price_comparator.contracts.CategoryRepository;


@RestController
@RequestMapping("/api/supplier-product")
public class SupplierProductController {

    private final SupplierProductService service;
    private final CategoryRepository categoryRepository;



    public SupplierProductController(SupplierProductService service, CategoryRepository categoryRepository) {
        this.service = service;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<SupplierProduct> getAll() {
        return service.get();
    }

    @GetMapping("/{id}")
    public SupplierProduct getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public SupplierProduct create(@RequestBody SupplierProduct supplierProduct) {
        return service.save(supplierProduct);
    }

    @PutMapping("/{id}")
    public SupplierProduct update(@PathVariable Long id, @RequestBody SupplierProduct supplierProduct) {
        return service.update(id, supplierProduct);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/best-discounts")
    public List<SupplierProduct> bestDiscounts(@RequestParam(defaultValue = "10") int limit) {
        return service.getBestDiscounts(limit);
    }

    @GetMapping("/best-discounts/{id}")
    public List<SupplierProduct> bestDiscountsById(@PathVariable Long id, @RequestParam(defaultValue = "10") int limit) {
        return service.getBestDiscountsBySupplierId(id,limit);
    }

    @GetMapping("/new-discounts")
    public List<SupplierProduct> newDiscounts() {
        return service.getNewDiscounts();
    }

    @GetMapping("/new-discounts/{id}")
    public List<SupplierProduct> newDiscounts(@PathVariable Long id) {
        return service.getNewDiscountsBySupplierId(id);
    }


    @GetMapping("/best-value")
    public List<SupplierProduct> getBestValuePerUnit(
            @RequestParam(required = false) String unit,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return service.getBestValuePerUnit(unit, limit);
    }
}
