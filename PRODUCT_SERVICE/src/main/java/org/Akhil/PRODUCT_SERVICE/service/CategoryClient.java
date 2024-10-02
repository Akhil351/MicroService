package org.Akhil.PRODUCT_SERVICE.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="CATEGORY-SERVICE",url="http://localhost:3003")
public interface CategoryClient {
    @GetMapping("/api/v2/categories/categoryName/{id}")
    public String categoryName(@PathVariable Long id);
}
