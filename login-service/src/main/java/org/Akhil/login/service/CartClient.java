package org.Akhil.login.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@FeignClient(name = "ECOMMERCE-SERVICE",url = "http://localhost:9003")
public interface CartClient {
    @GetMapping("/api/v2/carts/getTotalPrice")
    public BigDecimal getTotalAmount();

}
