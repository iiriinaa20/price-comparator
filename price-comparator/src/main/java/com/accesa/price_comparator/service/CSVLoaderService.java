package com.accesa.price_comparator.service;


import com.opencsv.CSVReaderBuilder;
import com.accesa.price_comparator.model.*;
import com.accesa.price_comparator.contracts.*;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

@Service
public class CSVLoaderService {

    private final SupplierProductRepository supplierProductRepo;
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final SupplierRepository supplierRepo;

    public CSVLoaderService(SupplierProductRepository supplierProductRepo,
                            ProductRepository productRepo,
                            CategoryRepository categoryRepo,
                            SupplierRepository supplierRepo) {
        this.supplierProductRepo = supplierProductRepo;
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.supplierRepo = supplierRepo;
    }

    public void loadProductsFromCsv(Long supplierId,  String filename) throws Exception {
        var supplier = supplierRepo.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        var reader = new CSVReaderBuilder(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename), "UTF-8")
        ).withSkipLines(1).build();

        List<String[]> rows = reader.readAll();

        for (String[] row : rows) {
            String productName = row[1].trim();
            String categoryName = row[2].trim();
            double quantity = Double.parseDouble(row[4].trim());
            String unit = row[5].trim();
            double price = Double.parseDouble(row[6].trim());
            String currency = row[7].trim();


            Category category = categoryRepo.findAll().stream()
                    .filter(c -> c.getName().equalsIgnoreCase(categoryName))
                    .findFirst()
                    .orElseGet(() ->
                    {
                        Category newCat = new Category();
                        newCat.setName(categoryName);
                        return categoryRepo.save(newCat);
                    });


            Product product = productRepo.findAll().stream()
                    .filter(p -> p.getName().equalsIgnoreCase(productName))
                    .findFirst()
                    .orElseGet(() -> {
                        Product newProduct = new Product();
                        newProduct.setName(productName);
                        newProduct.setCategory(category);
                        return productRepo.save(newProduct);
                    });


            SupplierProduct sp = new SupplierProduct();
            sp.setSupplier(supplier);
            sp.setProduct(product);
            sp.setQuantity(quantity);
            sp.setUnit(unit);
            sp.setCurrency(currency);
            sp.setBasePrice(price);
            sp.setDiscountPrice(null);
            sp.setDiscount(null);
            sp.setStart(null);
            sp.setEnd(null);

            supplierProductRepo.save(sp);
        }
    }

    public void loadDiscountsFromCsv(Long supplierId, String filename) throws Exception {
        var supplier = supplierRepo.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        var reader = new CSVReaderBuilder(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename), "UTF-8")
        ).withSkipLines(1).build();

        List<String[]> rows = reader.readAll();

        for (String[] row : rows) {
            String productName = row[1].trim();
            String categoryName = row[5].trim();

            LocalDate fromDate = LocalDate.parse(row[6].trim());
            LocalDate toDate = LocalDate.parse(row[7].trim());
            double discountPercent = Double.parseDouble(row[8].trim());


            Product product = productRepo.findAll().stream()
                    .filter(p -> p.getName().equalsIgnoreCase(productName) &&
                            p.getCategory().getName().equalsIgnoreCase(categoryName))
                    .findFirst()
                    .orElse(null);

            if (product == null) continue;


            List<SupplierProduct> matching = supplierProductRepo.findAll().stream()
                    .filter(sp -> sp.getSupplier().getId() == supplierId
                            && sp.getProduct().getId() == product.getId())
                    .toList();

            for (SupplierProduct sp : matching) {
                sp.setDiscount(discountPercent);
                sp.setStart(fromDate);
                sp.setEnd(toDate);
                sp.setDiscountPrice(
                        sp.getBasePrice() * (1 - discountPercent / 100.0)
                );
                supplierProductRepo.save(sp);
            }
        }
    }

}