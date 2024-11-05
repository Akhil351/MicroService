package org.Akhil.cart.controller;

import org.Akhil.cart.service.CartService;
import org.Akhil.common.dto.CartDto;
import org.Akhil.common.model.UserRequestContext;
import org.Akhil.common.response.ApiResponse;
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
    @Autowired
    private UserRequestContext context;

    @GetMapping("/my-cart")
    public ResponseEntity<ApiResponse> getCart(){
        CartDto cart=cartService.displayCurrentUserCart(context.getUserId());
        return ResponseEntity.ok(ApiResponse.builder().status("Success").data(cart).build());
    }

    @GetMapping("/{id}/cart/total/price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long id){
            BigDecimal totalPrice=cartService.getTotalPrice(id);
            return ResponseEntity.ok(ApiResponse.builder().status("Success").data(totalPrice).build());
    }
}
