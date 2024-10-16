package org.Akhil.cart.controller;

import org.Akhil.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitializeController {
    @Autowired
    private CartService cartService;

    @PostMapping("/initializeNewCart")
    public Long initializeNewCart(@RequestParam String userId){
        return cartService.initializeNewCart(userId);
    }
}
