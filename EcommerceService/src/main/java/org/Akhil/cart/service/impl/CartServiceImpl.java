package org.Akhil.cart.service.impl;

import jakarta.transaction.Transactional;
import org.Akhil.cart.service.CartService;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.model.Cart;
import org.Akhil.common.repo.CartItemRepo;
import org.Akhil.common.repo.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Override
    public Cart getCart(Long id) {
        return cartRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cart Not Found"));
    }

    @Transactional
    @Override
    public void clearCart(String userId) {
        Cart cart=cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
        cartItemRepo.deleteAllByCartId(cart.getId());
        cartRepo.delete(cart);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart=this.getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart(String userId) {
        Cart cart=new Cart();
        cart.setUserId(userId);
        return cartRepo.save(cart).getId();
    }

    @Override
    public Cart getCartByUserId(String userId) {
        return cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
    }
}
