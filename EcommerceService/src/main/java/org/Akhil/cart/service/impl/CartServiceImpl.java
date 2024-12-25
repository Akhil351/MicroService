package org.Akhil.cart.service.impl;

import jakarta.transaction.Transactional;
import org.Akhil.cart.service.CartService;
import org.Akhil.common.dto.CartDto;
import org.Akhil.common.dto.CartItemDto;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.mapper.Converter;
import org.Akhil.common.model.Cart;
import org.Akhil.common.model.CartItem;
import org.Akhil.common.repo.CartItemRepo;
import org.Akhil.common.repo.CartRepo;
import org.Akhil.common.repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private Converter converter;
    @Autowired
    private ProductRepo productRepo;

    private static final Logger logger= LoggerFactory.getLogger(CartServiceImpl.class);
    @Override
    public Cart getCart(String id) {
        return cartRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cart Not Found"));
    }

    @Transactional
    @Override
    public void deleteCart(String userId) {
        Cart cart=cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
        cartItemRepo.deleteAllByCartId(cart.getId());
        cartRepo.delete(cart);
    }
    @KafkaListener(topics ="${spring.kafka.topic2.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void removeCart(String userId){
        logger.info("deleting cart of the give user");
        this.deleteCart(userId);
    }

    @Override
    public BigDecimal getTotalPrice(String userId) {
        Cart cart=cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
        return cart.getTotalAmount();
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void createCart(String userId){
        logger.info("userId:{}", userId);
        Cart cart=new Cart();
        cart.setId("ca"+ UUID.randomUUID().toString());
        cart.setUserId(userId);
        cartRepo.save(cart);
    }

    @Override
    public Cart getCartByUserId(String userId) {
        return cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
    }
    private CartDto convertToCartDto(Cart cart){
        CartDto cartDto=converter.convertToDto(cart, CartDto.class);
        cartDto.setCartItems(cartItemRepo.findByCartId(cart.getId()).stream().map(this::convertToCartItemDto).toList());
        return cartDto;
    }
    private CartItemDto convertToCartItemDto(CartItem cartItem){
        CartItemDto cartItemDto= converter.convertToDto(cartItem, CartItemDto.class);
        List<Object[]> obj=productRepo.getNameAndImage(cartItem.getProductId()).orElseThrow(()->new ResourceNotFoundException("product not found"));
        Object[] productDetails = obj.getFirst();
        cartItemDto.setProductName((String) productDetails[0]);
        cartItemDto.setProductImageUrl((String) productDetails[1]);
        return cartItemDto;
    }

    @Transactional
    @Override
    public void clearCart(String cartId) {
        Cart cart=cartRepo.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart Not found"));
        cart.setTotalAmount(BigDecimal.ZERO);
        cartItemRepo.deleteAllByCartId(cart.getId());
    }

    @Override
    public CartDto displayCurrentUserCart(String userId) {
        Cart cart=cartRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
        return convertToCartDto(cart);
    }
}
