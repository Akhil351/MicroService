package org.Akhil.cart.service.impl;


import org.Akhil.cart.service.CartItemService;
import org.Akhil.cart.service.CartService;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.model.Cart;
import org.Akhil.common.model.CartItem;
import org.Akhil.common.repo.CartItemRepo;
import org.Akhil.common.repo.CartRepo;
import org.Akhil.common.repo.ProductRepo;
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
    private ProductRepo productRepo;

    @Override
    public void addItemToCart(String userId, String productId, int quantity) {
        Cart cart=cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
        CartItem cartItem=cartItemRepo.findByCartIdAndProductId(cart.getId(),productId).orElse(new CartItem());
        BigDecimal price=productRepo.getPrice(productId).orElseThrow(()->new ResourceNotFoundException("product not found"));
        if(ObjectUtils.isEmpty(cartItem.getId())){
            cartItem.setId("ci"+ UUID.randomUUID().toString());
            cartItem.setCartId(cart.getId());
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(price);
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
    public void removeItemFromCart(String userId, String itemId) {
           Cart cart=cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
           CartItem cartItemToRemove=cartItemRepo.findById(itemId).orElseThrow(()->new ResourceNotFoundException("CartItem not Found"));
           cartItemToRemove.setCartId(null);
           cartItemRepo.delete(cartItemToRemove);
           cart.setTotalAmount(this.updateTotalAmountInCart(cart));
           cartRepo.save(cart);
    }

    @Override
    public void updateItemQuantity(String userId, String itemId, int quantity) {
        Cart cart=cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
        CartItem cartItem=cartItemRepo.findById(itemId).orElseThrow(()->new ResourceNotFoundException("CartItem not Found"));
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
