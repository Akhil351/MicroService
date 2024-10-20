package org.Akhil.cart.controller;

import org.Akhil.cart.service.CartItemService;
import org.Akhil.cart.service.CartService;
import org.Akhil.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;



@RestController
@RequestMapping("/api/v2/cartItems")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam String userId,@RequestParam Long productId,@RequestParam Integer quantity){
               cartItemService.addItemToCart(userId,productId,quantity);
               return ResponseEntity.ok(ApiResponse.builder().message("Added Item SuccessFully").data(null).build());
    }
    @DeleteMapping("/cart/{cartId}/item/{productId}/remove")
    public ResponseEntity<ApiResponse> removeFromCart(@PathVariable Long cartId,@PathVariable Long productId){
            cartItemService.removeItemFromCart(cartId,productId);
            return ResponseEntity.ok(ApiResponse.builder().message("Removed Item SuccessFully").data(null).build());
    }

    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateCart(@PathVariable Long cartId,@PathVariable Long productId,@RequestParam Integer quantity){
            cartItemService.updateItemQuantity(cartId,productId,quantity);
            return ResponseEntity.ok(ApiResponse.builder().message("Updated Item SuccessFully").data(null).build());
    }
}
