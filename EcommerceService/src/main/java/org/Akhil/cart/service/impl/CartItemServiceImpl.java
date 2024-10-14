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
    public void addItemToCart(String userId, Long productId, int quantity) {
        Cart cart=cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
        Product product=productClient.getProductById(productId);
        CartItem cartItem=cartItemRepo.findByCartIdAndProductId(cart.getId(),productId).orElse(new CartItem());
        if(ObjectUtils.isEmpty(cartItem.getId())){
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
    public void removeItemFromCart(Long cartId, Long productId) {
           Cart cart=cartService.getCart(cartId);
           CartItem cartItemToRemove=this.getCartItem(cartId,productId);
           cartItemToRemove.setCartId(null);
           cartItemRepo.delete(cartItemToRemove);
           cart.setTotalAmount(this.updateTotalAmountInCart(cart));
           cartRepo.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart=cartService.getCart(cartId);
        CartItem cartItem=this.getCartItem(cartId,productId);
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();
        cartItemRepo.save(cartItem);
        cart.setTotalAmount(this.updateTotalAmountInCart(cart));
        cartRepo.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
       return cartItemRepo.findByCartIdAndProductId(cartId,productId).orElseThrow(()->new ResourceNotFoundException("Item Not Found"));
    }

    private BigDecimal updateTotalAmountInCart(Cart cart){
         return  cartItemRepo.findByCartId(cart.getId()).stream()
                .map(item->{
                    return item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                }).reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
