package org.Akhil.product.service;

import org.Akhil.common.dto.ProductDto;
import org.Akhil.common.model.Product;

import java.util.List;
import java.util.Map;

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
    List<Product> searchKey(Map<String,Object> params);
}
