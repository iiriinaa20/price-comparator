package com.accesa.price_comparator.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accesa.price_comparator.contracts.SupplierRepository;
import com.accesa.price_comparator.model.Supplier;

@Service
public class SupplierService {

    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public List<Supplier> get() {
        return repository.findAll();
    }

    public Supplier getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    public Supplier save(Supplier supplier) {
        return repository.save(supplier);
    }

    public Supplier update(Long id, Supplier updated) {
        Supplier existing = getById(id);
        existing.setName(updated.getName());
        existing.setImageUrl(updated.getImageUrl());
        existing.setWebsiteUrl(updated.getWebsiteUrl());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
