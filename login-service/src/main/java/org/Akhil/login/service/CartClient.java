package org.Akhil.login.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ECOMMERCE-SERVICE",url = "http://localhost:3003")
public interface CartClient {
    @PostMapping("/api/v2/carts/initializeNewCart")
    public void initializeNewCart(@RequestParam String userId);
}
