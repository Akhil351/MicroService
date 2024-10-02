package org.Akhil.CART_ITEM_SERVICE.service;

import org.Akhil.COMMON_SERVICE.model.CartItem;

public interface CartItemService {
    void addItemToCart(Long cartId,Long productId,int quantity);
    void removeItemFromCart(Long cartId,Long productId);
    void updateItemQuantity(Long cartId,Long productId,int quantity);
    CartItem getCartItem(Long cartId,Long productId);
}
