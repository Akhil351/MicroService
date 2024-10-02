package org.Akhil.CART_SERVICE.service.impl;

import jakarta.transaction.Transactional;
import org.Akhil.CART_SERVICE.service.CartService;
import org.Akhil.COMMON_SERVICE.exception.ResourceNotFoundException;
import org.Akhil.COMMON_SERVICE.model.Cart;
import org.Akhil.COMMON_SERVICE.repo.CartItemRepo;
import org.Akhil.COMMON_SERVICE.repo.CartRepo;
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
    public void clearCart(Long id) {
        Cart cart=this.getCart(id);
        cartItemRepo.deleteAllByCartId(id);
        cart.getCartItemIds().clear();
        cartRepo.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart=this.getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart cart=new Cart();
        return cartRepo.save(cart).getId();
    }
}
