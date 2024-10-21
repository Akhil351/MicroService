package org.Akhil.cart.controller;

import org.Akhil.cart.service.CartService;
import org.Akhil.common.response.ApiResponse;
import org.Akhil.common.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import java.math.BigDecimal;


@RestController
@RequestMapping("/api/v2/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{id}/my-cart")
    public ResponseEntity<Cart> getCart(@PathVariable Long id){
            Cart cart=cartService.getCart(id);
            return ResponseEntity.ok(cart);
    }

    @GetMapping("/{id}/cart/total/price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long id){
            BigDecimal totalPrice=cartService.getTotalPrice(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Total Price").data(totalPrice).build());
    }
}
