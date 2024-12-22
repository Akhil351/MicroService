package org.Akhil.cart.controller;

import org.Akhil.cart.service.CartItemService;
import org.Akhil.cart.service.CartService;
import org.Akhil.common.model.UserRequestContext;
import org.Akhil.common.response.ApiResponse;
import org.Akhil.common.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/api/v2/cartItems")
@CrossOrigin(JwtUtils.BASE_URL)
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;
    @Autowired
    private UserRequestContext context;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam String productId,@RequestParam Integer quantity){
               cartItemService.addItemToCart(context.getUserId(),productId,quantity);
               return ResponseEntity.ok(ApiResponse.builder().status("Success").data("cart added").build());
    }
    @DeleteMapping("/cartItem/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeFromCart(@PathVariable String itemId){
            cartItemService.removeItemFromCart(context.getUserId(),itemId);
            return ResponseEntity.ok(ApiResponse.builder().status("Success").data("cart removed").build());
    }

    @PutMapping("/cartItem/{itemId}/update")
    public ResponseEntity<ApiResponse> updateCart(@PathVariable String itemId,@RequestParam Integer quantity){
            cartItemService.updateItemQuantity(context.getUserId(),itemId,quantity);
            return ResponseEntity.ok(ApiResponse.builder().status("Success").data("cart updated").build());
    }
}
