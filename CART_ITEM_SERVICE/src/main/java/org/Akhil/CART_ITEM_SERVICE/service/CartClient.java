package org.Akhil.CART_ITEM_SERVICE.service;

import org.Akhil.COMMON_SERVICE.model.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="CART-SERVICE",url="http://localhost:6006")
public interface CartClient {
    @GetMapping("/api/v2/carts/{id}/my-cart")
    public Cart getCart(@PathVariable Long id);
    @GetMapping("/api/v2/carts/initializeNewCart")
    public Long getCartId();
}
