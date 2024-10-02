package org.Akhil.CART_ITEM_SERVICE.service;

import org.Akhil.COMMON_SERVICE.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="PRODUCT-SERVICE",url="http://localhost:4004")
public interface ProductClient {
    @GetMapping("/api/v2/products/getProductById/{id}")
    public Product getProductById(@PathVariable("id") Long productId);
}
