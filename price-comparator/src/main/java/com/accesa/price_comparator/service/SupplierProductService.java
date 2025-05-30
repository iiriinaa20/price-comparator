package com.accesa.price_comparator.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Comparator;
import java.time.temporal.IsoFields;

import org.springframework.stereotype.Service;

import com.accesa.price_comparator.contracts.ProductRepository;
import com.accesa.price_comparator.contracts.SupplierProductRepository;
import com.accesa.price_comparator.contracts.SupplierRepository;
import com.accesa.price_comparator.model.SupplierProduct;
import com.accesa.price_comparator.dto.ProductHistoryDto;
import com.accesa.price_comparator.model.Category;

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



    public List<SupplierProduct> getBestDiscounts(int limit) {
        return repo.findByDiscountNotNullOrderByDiscountDesc()
                .stream()
                .limit(limit)
                .toList();
    }

    public List<SupplierProduct> getBestDiscountsBySupplierId(Long id, int limit) {
        return repo.findByDiscountNotNullOrderByDiscountDesc()
                .stream()
                .filter(sp -> sp.getSupplier().getId() == id)
                .limit(limit)
                .toList();
    }

    public List<SupplierProduct> getNewDiscounts() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return repo.findByStartAfter(yesterday);

    }


    public List<SupplierProduct> getNewDiscountsBySupplierId(Long id) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return repo.findByStartAfter(yesterday)
                .stream().filter(sp->sp.getSupplier().getId() == id )
                .toList();

    }


    public List<ProductHistoryDto> getPriceHistoryBySupplierAndProduct(Long productId, Long supplierId) {
        return repo.findByProductIdAndSupplierIdOrderByIdAsc(productId, supplierId).stream()
                .map(sp -> new ProductHistoryDto(
                        sp.getProduct().getName(),
                        sp.getStart(),
                        sp.getDiscountPrice() != null
                                ? sp.getDiscountPrice()
                                : sp.getBasePrice()
                ))
                .toList();
    }

    public List<ProductHistoryDto> getPriceHistoryByCatagoryAndProduct(Long productId, Category category) {
        return repo.findByProductIdAndProductCategoryOrderByIdAsc(productId, category).stream()
                .map(sp -> new ProductHistoryDto(
                        sp.getProduct().getName(),
                        sp.getStart(),
                        sp.getDiscountPrice() != null ? sp.getDiscountPrice() : sp.getBasePrice()
                ))
                .toList();
    }

    public List<SupplierProduct> getBestValuePerUnit(String unit, int limit) {
        return repo.findAll().stream()
                .filter(p -> p.getQuantity() > 0)
                .filter(p -> unit == null || unit.equalsIgnoreCase(p.getUnit()))
                .sorted(Comparator.comparingDouble(SupplierProduct::getValuePerUnit))
                .limit(limit)
                .toList();
    }

}
