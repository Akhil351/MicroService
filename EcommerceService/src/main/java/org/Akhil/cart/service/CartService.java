package org.Akhil.cart.service;

import org.Akhil.common.dto.CartDto;
import org.Akhil.common.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void deleteCart(String userId);
    BigDecimal getTotalPrice(String userId);
    Long initializeNewCart(String userId);
    Cart getCartByUserId(String userId);
    void clearCart(Long cartId);
    CartDto displayCurrentUserCart(String userId);
}
