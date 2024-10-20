package org.Akhil.login.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ECOMMERCE-SERVICE",url = "http://localhost:9003")
public interface CartClient {
    @PostMapping("/initializeNewCart")
    public void initializeNewCart(@RequestParam String userId);
}
