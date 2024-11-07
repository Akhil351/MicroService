package org.Akhil.cart.service;

import org.Akhil.common.model.CartItem;

public interface CartItemService {
    void addItemToCart(String userId,String productId,int quantity);
    void removeItemFromCart(String userId,String productId);
    void updateItemQuantity(String userId,String productId,int quantity);
    CartItem getCartItem(String cartId,String productId);
}
