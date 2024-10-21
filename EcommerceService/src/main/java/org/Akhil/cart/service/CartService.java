package org.Akhil.cart.service;

import org.Akhil.common.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void deleteCart(String userId);
    BigDecimal getTotalPrice(Long id);
    Long initializeNewCart(String userId);
    Cart getCartByUserId(String userId);
    void clearCart(Long cartId);
}
