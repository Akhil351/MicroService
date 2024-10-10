package org.Akhil.cart.service;

import org.Akhil.common.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="PRODUCT-SERVICE",url="http://localhost:8001")
public interface ProductClient {
    @GetMapping("/api/v2/products/getProductById/{id}")
    public Product getProductById(@PathVariable("id") Long productId);
}
