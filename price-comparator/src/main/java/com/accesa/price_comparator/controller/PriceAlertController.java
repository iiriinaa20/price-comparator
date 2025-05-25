package com.accesa.price_comparator.controller;

import com.accesa.price_comparator.dto.PriceAlertDto;
import com.accesa.price_comparator.model.SupplierProduct;
import com.accesa.price_comparator.service.PriceAlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class PriceAlertController {

    private final PriceAlertService priceAlertService;

    public PriceAlertController(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @RequestMapping(value = "/set", method = {RequestMethod.GET, RequestMethod.POST})
    public String setAlert(@RequestParam Long productId, @RequestParam Double targetPrice) {
        return priceAlertService.setPriceAlert(productId, targetPrice);
    }


    @GetMapping("/check-grouped")
    public List<PriceAlertDto> checkGroupedAlerts() {
        return priceAlertService.checkAlertMatchesGrouped();
    }
}
