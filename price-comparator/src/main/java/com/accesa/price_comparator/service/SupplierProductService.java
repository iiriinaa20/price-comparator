package com.accesa.price_comparator.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accesa.price_comparator.contracts.ProductRepository;
import com.accesa.price_comparator.contracts.SupplierProductRepository;
import com.accesa.price_comparator.contracts.SupplierRepository;
import com.accesa.price_comparator.model.SupplierProduct;

@Service
public class SupplierProductService {

    private final SupplierProductRepository repo;
    private final SupplierRepository supplierRepo;
    private final ProductRepository productRepo;

    public SupplierProductService(SupplierProductRepository repo, SupplierRepository supplierRepo,
            ProductRepository productRepo) {
        this.repo = repo;
        this.supplierRepo = supplierRepo;
        this.productRepo = productRepo;
    }

    public List<SupplierProduct> get() {
        return repo.findAll();
    }

    public SupplierProduct getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("SupplierProduct not found"));
    }

    public SupplierProduct save(SupplierProduct sp) {
        supplierRepo.findById(sp.getSupplier().getId())
                .orElseThrow(() -> new RuntimeException("Invalid supplier ID"));
        productRepo.findById(sp.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Invalid product ID"));
        return repo.save(sp);
    }

    public SupplierProduct update(Long id, SupplierProduct sp) {
        SupplierProduct existing = getById(id);
        existing.setSupplier(sp.getSupplier());
        existing.setProduct(sp.getProduct());
        existing.setUnit(sp.getUnit());
        existing.setQuantity(sp.getQuantity());
        existing.setCurrency(sp.getCurrency());
        existing.setBasePrice(sp.getBasePrice());
        existing.setDiscount(sp.getDiscount());
        existing.setDiscountPrice(sp.getDiscountPrice());
        existing.setStart(sp.getStart());
        existing.setEnd(sp.getEnd());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
