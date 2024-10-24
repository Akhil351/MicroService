package org.Akhil.product.controller;

import org.Akhil.common.response.ApiResponse;
import org.Akhil.common.dto.ProductDto;
import org.Akhil.common.model.Product;
import org.Akhil.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v2/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long productId){
            Product product=productService.getProductById(productId);
            return ResponseEntity.ok(product);
    }

    @PreAuthorize("@securityValidate.isAdmin()")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto){
            ProductDto product=productService.convertToDto(productService.addProduct(productDto));
            return ResponseEntity.ok(ApiResponse.builder().message("Product Added").data(product).build());
    }

    @PreAuthorize("@securityValidate.isAdmin()")
    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDto productDto,@PathVariable Long productId){
            ProductDto product=productService.convertToDto(productService.updateProduct(productDto,productId));
            return ResponseEntity.ok(ApiResponse.builder().message("Updated Product SuccessFully").data(product).build());
    }

    @PreAuthorize("@securityValidate.isAdmin()")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
            productService.deleteProductById(productId);
            return ResponseEntity.ok(ApiResponse.builder().message("Product Deleted SuccessFully").data(null).build());
    }

    @GetMapping("/countProductsByBrandAndName")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brandName,@RequestParam String productName){
            Long count=productService.countProductsByBrandAndName(brandName,productName);
            return ResponseEntity.ok(ApiResponse.builder().message("Success").data(count).build());
    }
    @GetMapping("/allProducts")
    public ResponseEntity<ApiResponse> allProducts(@RequestBody Map<String,Object> params){
        List<ProductDto> products=productService.getConvertedProducts(productService.searchKey(params));
        return ResponseEntity.ok(ApiResponse.builder().message("Success").data(products).build());
    }
}
