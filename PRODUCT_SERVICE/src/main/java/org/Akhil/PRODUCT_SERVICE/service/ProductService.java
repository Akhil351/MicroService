package org.Akhil.PRODUCT_SERVICE.service;

import org.Akhil.COMMON_SERVICE.dto.ProductDto;
import org.Akhil.COMMON_SERVICE.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductDto productDto);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductDto productDto,Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand,String name);
    public Long countProductsByBrandAndName(String brand,String name);
    ProductDto convertToDto(Product product);
    List<ProductDto> getConvertedProducts(List<Product> products);
}
