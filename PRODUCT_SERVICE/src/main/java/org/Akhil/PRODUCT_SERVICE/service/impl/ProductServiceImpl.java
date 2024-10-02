package org.Akhil.PRODUCT_SERVICE.service.impl;

import org.Akhil.COMMON_SERVICE.dto.ImageDto;
import org.Akhil.COMMON_SERVICE.dto.ProductDto;
import org.Akhil.COMMON_SERVICE.exception.ResourceNotFoundException;
import org.Akhil.COMMON_SERVICE.model.Category;
import org.Akhil.COMMON_SERVICE.model.Product;
import org.Akhil.COMMON_SERVICE.repo.CategoryRepo;
import org.Akhil.COMMON_SERVICE.repo.ImageRepo;
import org.Akhil.COMMON_SERVICE.repo.ProductRepo;
import org.Akhil.PRODUCT_SERVICE.service.CategoryClient;
import org.Akhil.PRODUCT_SERVICE.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ImageRepo imageRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryClient categoryClient;
    @Override
    public Product addProduct(ProductDto productDto) {
        Category category=this.getCategory(productDto.getCategory());
        return productRepo.save(this.createProduct(productDto,category.getId()));
    }
    private Category getCategory(String categoryName){
        return  categoryRepo.findByName(categoryName)
                .orElseGet(()->{
                    Category newCategory=Category.builder().name(categoryName).build();
                    return categoryRepo.save(newCategory);
                });
    }
    private Product createProduct(ProductDto productDto,Long categoryId){
        return Product.builder()
                .name(productDto.getName())
                .brand(productDto.getBrand())
                .price(productDto.getPrice())
                .inventory(productDto.getInventory())
                .description(productDto.getDescription())
                .categoryId(categoryId)
                .build();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Product Not Found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepo.findById(id).ifPresentOrElse(productRepo::delete,()->{throw new ResourceNotFoundException("Product Not Found");});
        imageRepo.deleteAllByProductId(id);
    }

    @Override
    public Product updateProduct(ProductDto productDto, Long productId) {
        return productRepo.findById(productId)
                .map(existingProduct->this.updateExistingProduct(existingProduct,productDto))
                .map(productRepo::save)
                .orElseThrow(()->new ResourceNotFoundException("Product Not Found"));
    }
    private Product updateExistingProduct(Product existingProduct,ProductDto productDto){
        if(!ObjectUtils.isEmpty(productDto.getName())) existingProduct.setName(productDto.getName());
        if(!ObjectUtils.isEmpty(productDto.getBrand())) existingProduct.setBrand(productDto.getBrand());
        if(!ObjectUtils.isEmpty(productDto.getPrice())) existingProduct.setPrice(productDto.getPrice());
        if(!ObjectUtils.isEmpty(productDto.getDescription())) existingProduct.setDescription(productDto.getDescription());
        if(productDto.getInventory()!=0) existingProduct.setInventory(productDto.getInventory());
        if(!ObjectUtils.isEmpty(productDto.getCategory()))  {
        Category category=this.getCategory(productDto.getCategory());
        existingProduct.setCategoryId(category.getId());
      }
       return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepo.findByCategoryId(this.getCategory(category).getId());
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepo.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepo.findByCategoryIdAndBrand(this.getCategory(category).getId(),brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepo.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepo.countByBrandAndNameContaining(brand,name);
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto=modelMapper.map(product,ProductDto.class);
        productDto.setCategory(categoryClient.categoryName(product.getCategoryId()));
        productDto.setImages(imageRepo.findByProductId(product.getId()).stream().map(image->modelMapper.map(image,ImageDto.class)).toList());
        return productDto;
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }
}
