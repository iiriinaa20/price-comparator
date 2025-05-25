package com.accesa.price_comparator.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreBasketDto {
    private String storeName;
    private List<BasketItemDto> items;
    private double totalPrice;
}