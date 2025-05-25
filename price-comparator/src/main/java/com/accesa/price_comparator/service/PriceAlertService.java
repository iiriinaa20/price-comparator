package com.accesa.price_comparator.service;

import com.accesa.price_comparator.contracts.*;
import com.accesa.price_comparator.dto.PriceAlertDto;
import com.accesa.price_comparator.model.*;

import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.*;

@Service
public class PriceAlertService {

    private final PriceAlertRepository priceAlertRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SupplierProductRepository supplierProductRepository;

    public PriceAlertService(PriceAlertRepository priceAlertRepository,
                             ProductRepository productRepository,
                             UserRepository userRepository,
                             SupplierProductRepository supplierProductRepository) {
        this.priceAlertRepository = priceAlertRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.supplierProductRepository = supplierProductRepository;
    }

    @Transactional
    public String setPriceAlert(Long productId, Double targetPrice) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        PriceAlert alert = new PriceAlert();
        alert.setProduct(product);
        alert.setUser(user);
        alert.setTargetPrice(targetPrice);

        priceAlertRepository.save(alert);
        return "Price alert saved.";
    }


    public List<PriceAlertDto> checkAlertMatchesGrouped() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<PriceAlert> alerts = priceAlertRepository.findByUser(user);
        List<PriceAlertDto> result = new ArrayList<>();

        for (PriceAlert alert : alerts) {
            List<SupplierProduct> supplierOffers = supplierProductRepository.findByProductId(alert.getProduct().getId());

            List<PriceAlertDto.OfferDto> matchingOffers = supplierOffers.stream()
                    .filter(sp -> sp.getEffectivePrice() <= alert.getTargetPrice())

                    .map(sp -> new PriceAlertDto.OfferDto(sp.getSupplier().getName(), sp.getEffectivePrice()))
                    .toList();

            if (!matchingOffers.isEmpty()) {
                result.add(new PriceAlertDto(
                        alert.getProduct().getId(),
                        alert.getProduct().getName(),
                        alert.getTargetPrice(),
                        matchingOffers
                ));
            }
        }

        return result;
    }
}
