package org.Akhil.cart.controller;

import org.Akhil.cart.service.CartService;
import org.Akhil.common.response.ApiResponse;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v2/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{id}/my-cart")
    public ResponseEntity<Cart> getCart(@PathVariable Long id){
        try{
            Cart cart=cartService.getCart(id);
            return ResponseEntity.ok(cart);
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}/clear")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable Long id){
        try{
            cartService.clearCart(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Cart Deleted Successfully").data(null).build());
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }

    @GetMapping("/{id}/cart/total/price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long id){
        try{
            BigDecimal totalPrice=cartService.getTotalPrice(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Total Price").data(totalPrice).build());
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }

    @PostMapping("/initializeNewCart")
    public Long initializeNewCart(@RequestParam String userId){
        return cartService.initializeNewCart(userId);
    }
}
