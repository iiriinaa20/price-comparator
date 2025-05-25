package com.accesa.price_comparator.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.accesa.price_comparator.contracts.BasketItemRepository;
import com.accesa.price_comparator.contracts.SupplierProductRepository;
import com.accesa.price_comparator.contracts.UserRepository;
import com.accesa.price_comparator.dto.BasketItemDto;
import com.accesa.price_comparator.dto.StoreBasketDto;
import com.accesa.price_comparator.model.BasketItem;
import com.accesa.price_comparator.model.SupplierProduct;
import com.accesa.price_comparator.model.User;

import jakarta.transaction.Transactional;

@Service
public class BasketOptimizerService {

    private final SupplierProductRepository supplierProductRepository;
    private final BasketItemRepository basketItemRepository;
    private final UserRepository userRepository;

    public BasketOptimizerService(
            SupplierProductRepository supplierProductRepository,
            BasketItemRepository basketItemRepository,
            UserRepository userRepository) {
        this.supplierProductRepository = supplierProductRepository;
        this.basketItemRepository = basketItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<StoreBasketDto> optimizeFromBasketId(Long basketId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<BasketItem> items = basketItemRepository.findByBasketId(basketId);

        if (items.isEmpty()) {
            throw new RuntimeException("Basket is empty or does not exist.");
        }

        if (!items.get(0).getBasket().getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to acces this basket");
        }

        Map<String, StoreBasketDto> result = new HashMap<>();

        for (BasketItem item : items) {
            Long productId = item.getProduct().getId();
            List<SupplierProduct> options = supplierProductRepository.findByProductId(productId);

            if (options.isEmpty())
                continue;

            SupplierProduct best = options.stream()
                    .min(Comparator.comparingDouble(SupplierProduct::getEffectivePrice))
                    .orElse(null);

            if (best == null)
                continue;

            String store = best.getSupplier().getName();
            double cost = best.getEffectivePrice() * item.getQuantity();

            StoreBasketDto dto = result.computeIfAbsent(store, k -> {
                StoreBasketDto sb = new StoreBasketDto();
                sb.setStoreName(k);
                sb.setItems(new ArrayList<>());
                sb.setTotalPrice(0.0);
                return sb;
            });

            dto.getItems().add(new BasketItemDto(String.valueOf(productId), item.getQuantity()));
            dto.setTotalPrice(dto.getTotalPrice() + cost);
        }

        return new ArrayList<>(result.values());
    }
}
