package org.Akhil.cart.controller;

import org.Akhil.cart.service.CartService;
import org.Akhil.common.dto.CartDto;
import org.Akhil.common.model.UserRequestContext;
import org.Akhil.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;


@RestController
@RequestMapping("/api/v2/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserRequestContext context;

    @GetMapping("/my-cart")
    public ResponseEntity<ApiResponse> getCart(){
        CartDto cart=cartService.displayCurrentUserCart(context.getUserId());
        return ResponseEntity.ok(ApiResponse.builder().status("Success").data(cart).build());
    }

    @GetMapping("/getTotalPrice")
    public BigDecimal getTotalAmount(){
        return cartService.getTotalPrice(context.getUserId());
    }

    @PostMapping("/initializeNewCart")
    public String initializeNewCart(@RequestParam String userId){
        return cartService.initializeNewCart(userId);
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable String userId){
        cartService.deleteCart(userId);
        return ResponseEntity.ok(ApiResponse.builder().status("Success").data("cart deleted").build());
    }
}
