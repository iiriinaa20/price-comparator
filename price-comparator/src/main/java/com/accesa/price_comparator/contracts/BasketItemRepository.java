package com.accesa.price_comparator.contracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accesa.price_comparator.model.BasketItem;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {
    List<BasketItem> findByBasketId(Long basketId);
}