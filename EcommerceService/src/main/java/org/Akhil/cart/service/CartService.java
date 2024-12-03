package org.Akhil.cart.service;

import org.Akhil.common.dto.CartDto;
import org.Akhil.common.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(String id);
    void deleteCart(String userId);
    BigDecimal getTotalPrice(String userId);
    Cart getCartByUserId(String userId);
    void clearCart(String cartId);
    CartDto displayCurrentUserCart(String userId);
}
