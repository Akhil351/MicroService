package org.Akhil.CART_SERVICE.service;

import org.Akhil.COMMON_SERVICE.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long initializeNewCart();
}
