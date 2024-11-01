package org.Akhil.cart.controller;

import org.Akhil.cart.service.CartService;
import org.Akhil.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class InitializeController {
    @Autowired
    private CartService cartService;

    @PostMapping("/initializeNewCart")
    public Long initializeNewCart(@RequestParam String userId){
        return cartService.initializeNewCart(userId);
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable String userId){
        cartService.deleteCart(userId);
        return ResponseEntity.ok(ApiResponse.builder().status("Success").data("cart deleted").build());
    }
}
