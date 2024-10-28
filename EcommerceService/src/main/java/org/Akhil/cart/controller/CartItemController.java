package org.Akhil.cart.controller;

import org.Akhil.cart.service.CartItemService;
import org.Akhil.cart.service.CartService;
import org.Akhil.common.config.userDetails.CustomerDetails;
import org.Akhil.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId,@RequestParam Integer quantity){
               Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
               CustomerDetails userDetails=(CustomerDetails) authentication.getPrincipal();
               cartItemService.addItemToCart(userDetails.getId(),productId,quantity);
               return ResponseEntity.ok(ApiResponse.builder().message("Added Item SuccessFully").data(null).build());
    }
    @DeleteMapping("/cartItem/{productId}/remove")
    public ResponseEntity<ApiResponse> removeFromCart(@RequestParam String userId,@PathVariable Long productId){
            cartItemService.removeItemFromCart(userId,productId);
            return ResponseEntity.ok(ApiResponse.builder().message("Removed Item SuccessFully").data(null).build());
    }

    @PutMapping("/cartItem/{productId}/update")
    public ResponseEntity<ApiResponse> updateCart(@RequestParam String userId,@PathVariable Long productId,@RequestParam Integer quantity){
            cartItemService.updateItemQuantity(userId,productId,quantity);
            return ResponseEntity.ok(ApiResponse.builder().message("Updated Item SuccessFully").data(null).build());
    }
}
