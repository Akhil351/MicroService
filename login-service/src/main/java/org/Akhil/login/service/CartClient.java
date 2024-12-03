package org.Akhil.login.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@FeignClient(name = "ECOMMERCE-SERVICE",url = "http://localhost:9003")
public interface CartClient {
    @DeleteMapping("/api/v2/carts/{userId}/clear")
    public void deleteCart(@PathVariable String userId);

    @GetMapping("/api/v2/carts/getTotalPrice")
    public BigDecimal getTotalAmount();

}
