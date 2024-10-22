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
    Long countProductsByBrandAndName(String brand,String name);
    ProductDto convertToDto(Product product);
    List<ProductDto> getConvertedProducts(List<Product> products);
    List<Product> searchKey(Map<String,Object> params);
}
