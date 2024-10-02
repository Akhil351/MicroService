package org.Akhil.PRODUCT_SERVICE.controller;

import org.Akhil.COMMON_SERVICE.dto.ApiResponse;
import org.Akhil.COMMON_SERVICE.dto.ProductDto;
import org.Akhil.COMMON_SERVICE.exception.ResourceNotFoundException;
import org.Akhil.COMMON_SERVICE.model.Product;
import org.Akhil.PRODUCT_SERVICE.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v2/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<ProductDto> products=productService.getConvertedProducts(productService.getAllProducts());
        return ResponseEntity.ok(ApiResponse.builder().message("success").data(products).build());
    }
    @GetMapping("/getProductById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long productId){
        try{
            Product product=productService.getProductById(productId);
            return ResponseEntity.ok(product);
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto){
        try{
            ProductDto product=productService.convertToDto(productService.addProduct(productDto));
            return ResponseEntity.ok(ApiResponse.builder().message("Product Added").data(product).build());
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDto productDto,@PathVariable Long productId){
        try{
            ProductDto product=productService.convertToDto(productService.updateProduct(productDto,productId));
            return ResponseEntity.ok(ApiResponse.builder().message("Updated Product SuccessFully").data(product).build());
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }


    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try{
           productService.deleteProductById(productId);
            return ResponseEntity.ok(ApiResponse.builder().message("Product Deleted SuccessFully").data(null).build());
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }

    @GetMapping("/getProductsByBrandAndName")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brandName,@RequestParam String productName){
        try{
           List<ProductDto> products=productService.getConvertedProducts(productService.getProductsByBrandAndName(brandName,productName));
           if(products.isEmpty()){
               return ResponseEntity.ok(ApiResponse.builder().message("Not Found").data(null).build());
           }
            return ResponseEntity.ok(ApiResponse.builder().message("Success").data(products).build());

        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }

    @GetMapping("/getProductsByCategoryAndBrand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String category,@RequestParam String brandName){
        try{
            List<ProductDto> products=productService.getConvertedProducts(productService.getProductsByCategoryAndBrand(category,brandName));
            if(products.isEmpty()){
                return ResponseEntity.ok(ApiResponse.builder().message("Not Found").data(null).build());
            }
            return ResponseEntity.ok(ApiResponse.builder().message("Success").data(products).build());

        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }

    @GetMapping("/getProductsByName/{name}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name){
        try{
            List<ProductDto> products=productService.getConvertedProducts(productService.getProductsByName(name));
            if(products.isEmpty()){
                return ResponseEntity.ok(ApiResponse.builder().message("Not Found").data(null).build());
            }
            return ResponseEntity.ok(ApiResponse.builder().message("Success").data(products).build());

        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }

    @GetMapping("/getProductsByBrand")
    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand){
        try{
            List<ProductDto> products=productService.getConvertedProducts(productService.getProductsByBrand(brand));
            if(products.isEmpty()){
                return ResponseEntity.ok(ApiResponse.builder().message("Not Found").data(null).build());
            }
            return ResponseEntity.ok(ApiResponse.builder().message("Success").data(products).build());

        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }

    @GetMapping("/getProductsByCategory/{category}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category){
        try{
            List<ProductDto> products=productService.getConvertedProducts(productService.getProductsByCategory(category));
            if(products.isEmpty()){
                return ResponseEntity.ok(ApiResponse.builder().message("Not Found").data(null).build());
            }
            return ResponseEntity.ok(ApiResponse.builder().message("Success").data(products).build());

        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }

    @GetMapping("/countProductsByBrandAndName")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brandName,@RequestParam String productName){
        try{
            Long count=productService.countProductsByBrandAndName(brandName,productName);
            return ResponseEntity.ok(ApiResponse.builder().message("Success").data(count).build());

        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
    }



}
