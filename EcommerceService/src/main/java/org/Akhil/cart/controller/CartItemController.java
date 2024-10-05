package org.Akhil.cart.controller;

import org.Akhil.cart.service.CartItemService;
import org.Akhil.cart.service.CartService;
import org.Akhil.common.response.ApiResponse;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v2/cartItems")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,@RequestParam Long productId,@RequestParam Integer quantity){
           try{
               if(ObjectUtils.isEmpty(cartId)){
                   cartId=cartService.initializeNewCart();
               }
               cartItemService.addItemToCart(cartId,productId,quantity);
               return ResponseEntity.ok(ApiResponse.builder().message("Added Item SuccessFully").data(null).build());
           }
           catch (ResourceNotFoundException e){
               return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
           }
    }
    @DeleteMapping("/cart/{cartId}/item/{productId}/remove")
    public ResponseEntity<ApiResponse> removeFromCart(@PathVariable Long cartId,@PathVariable Long productId){
        try{

            cartItemService.removeItemFromCart(cartId,productId);
            return ResponseEntity.ok(ApiResponse.builder().message("Removed Item SuccessFully").data(null).build());
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }

    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateCart(@PathVariable Long cartId,@PathVariable Long productId,@RequestParam Integer quantity){
        try{
            cartItemService.updateItemQuantity(cartId,productId,quantity);
            return ResponseEntity.ok(ApiResponse.builder().message("Updated Item SuccessFully").data(null).build());
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }
}
