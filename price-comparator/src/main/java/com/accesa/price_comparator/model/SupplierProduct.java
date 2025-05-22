package com.accesa.price_comparator.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class SupplierProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Supplier supplier;

    @ManyToOne
    private Product product;

    private String unit;
    private double quantity;
    private String currency;
    private double basePrice;
    private Double discount;
    private Double discountPrice;

    private LocalDate start;
    private LocalDate end;
}
