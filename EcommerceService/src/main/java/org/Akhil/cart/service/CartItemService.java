package org.Akhil.cart.service;

import org.Akhil.common.model.CartItem;

public interface CartItemService {
    void addItemToCart(String userId,Long productId,int quantity);
    void removeItemFromCart(String userId,Long productId);
    void updateItemQuantity(String userId,Long productId,int quantity);
    CartItem getCartItem(Long cartId,Long productId);
}
