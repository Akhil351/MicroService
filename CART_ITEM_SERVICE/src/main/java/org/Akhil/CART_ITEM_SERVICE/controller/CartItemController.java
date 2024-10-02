package org.Akhil.CART_ITEM_SERVICE.controller;

import org.Akhil.CART_ITEM_SERVICE.service.CartClient;
import org.Akhil.CART_ITEM_SERVICE.service.CartItemService;
import org.Akhil.COMMON_SERVICE.dto.ApiResponse;
import org.Akhil.COMMON_SERVICE.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v2/cartItems")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CartClient cartClient;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,@RequestParam Long productId,@RequestParam Integer quantity){
           try{
               if(ObjectUtils.isEmpty(cartId)){
                   cartId=cartClient.getCartId();
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
