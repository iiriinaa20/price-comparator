package com.accesa.price_comparator.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accesa.price_comparator.service.CSVLoaderService;

@RestController
@RequestMapping("/api/import")
public class CsvImportController {

    private final CSVLoaderService service;

    public CsvImportController(CSVLoaderService service) {
        this.service = service;
    }

    @PostMapping("/{supplierId}")
    public String importProducts(
            @PathVariable Long supplierId,
            @RequestParam String filename) throws Exception {
        service.loadProductsFromCsv(supplierId, filename);
        return "Imported successfully.";
    }

    @PostMapping("/discounts/{supplierId}")
    public String importDiscounts(
            @PathVariable Long supplierId,
            @RequestParam String filename) throws Exception {
        service.loadDiscountsFromCsv(supplierId, filename);
        return "Discounts imported successfully.";
    }
}
