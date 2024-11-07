package org.Akhil.cart.service.impl;


import org.Akhil.cart.service.CartItemService;
import org.Akhil.cart.service.CartService;
import org.Akhil.cart.service.ProductClient;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.model.Cart;
import org.Akhil.common.model.CartItem;
import org.Akhil.common.model.Product;
import org.Akhil.common.repo.CartItemRepo;
import org.Akhil.common.repo.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.UUID;


@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductClient productClient;
    @Override
    public void addItemToCart(String userId, String productId, int quantity) {
        Cart cart=cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
        Product product=productClient.getProductById(productId);
        CartItem cartItem=cartItemRepo.findByCartIdAndProductId(cart.getId(),productId).orElse(new CartItem());
        if(ObjectUtils.isEmpty(cartItem.getId())){
            cartItem.setId("cartItem"+ UUID.randomUUID().toString());
            cartItem.setCartId(cart.getId());
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        cartItem.setTotalPrice();
        cartItemRepo.save(cartItem);
        cart.setTotalAmount(this.updateTotalAmountInCart(cart));
        cartRepo.save(cart);
    }

    @Override
    public void removeItemFromCart(String userId, String productId) {
           Cart cart=cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
           CartItem cartItemToRemove=this.getCartItem(cart.getId(),productId);
           cartItemToRemove.setCartId(null);
           cartItemRepo.delete(cartItemToRemove);
           cart.setTotalAmount(this.updateTotalAmountInCart(cart));
           cartRepo.save(cart);
    }

    @Override
    public void updateItemQuantity(String userId, String productId, int quantity) {
        Cart cart=cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
        CartItem cartItem=this.getCartItem(cart.getId(),productId);
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();
        cartItemRepo.save(cartItem);
        cart.setTotalAmount(this.updateTotalAmountInCart(cart));
        cartRepo.save(cart);
    }

    @Override
    public CartItem getCartItem(String cartId, String productId) {
       return cartItemRepo.findByCartIdAndProductId(cartId,productId).orElseThrow(()->new ResourceNotFoundException("Item Not Found"));
    }

    private BigDecimal updateTotalAmountInCart(Cart cart){
         return  cartItemRepo.findByCartId(cart.getId()).stream()
                .map(item->{
                    return item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                }).reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
