package com.accesa.price_comparator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PriceAlertDto {
    private Long productId;
    private String productName;
    private Double targetPrice;
    private List<OfferDto> matches;

    @Data
    @AllArgsConstructor
    public static class OfferDto {
        private String supplierName;
        private Double price;
    }
}
