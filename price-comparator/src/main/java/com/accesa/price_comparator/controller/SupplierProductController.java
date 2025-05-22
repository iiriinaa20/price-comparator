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

import com.accesa.price_comparator.model.SupplierProduct;
import com.accesa.price_comparator.service.SupplierProductService;

@RestController
@RequestMapping("/api/supplier-product")
public class SupplierProductController {

    private final SupplierProductService service;

    public SupplierProductController(SupplierProductService service) {
        this.service = service;
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
}
