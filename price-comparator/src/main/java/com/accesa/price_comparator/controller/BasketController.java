package com.accesa.price_comparator.controller;

import com.accesa.price_comparator.dto.BasketItemDto;
import com.accesa.price_comparator.dto.StoreBasketDto;
import com.accesa.price_comparator.service.BasketOptimizerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    private final BasketOptimizerService basketOptimizerService;

    public BasketController(BasketOptimizerService basketOptimizerService) {
        this.basketOptimizerService = basketOptimizerService;
    }

    @GetMapping("/optimize/{basketId}")
    public List<StoreBasketDto> optimizeBasket(@PathVariable Long basketId) {
        return basketOptimizerService.optimizeFromBasketId(basketId);
    }
    }

