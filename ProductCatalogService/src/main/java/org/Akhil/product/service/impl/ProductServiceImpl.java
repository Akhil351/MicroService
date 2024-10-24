package org.Akhil.product.service.impl;

import org.Akhil.common.dto.ImageDto;
import org.Akhil.common.dto.ProductDto;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.mapper.Converter;
import org.Akhil.common.model.Category;
import org.Akhil.common.model.Product;
import org.Akhil.common.repo.CategoryRepo;
import org.Akhil.common.repo.ImageRepo;
import org.Akhil.common.repo.ProductRepo;
import org.Akhil.common.specification.SpecificationBuilder;
import org.Akhil.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ImageRepo imageRepo;
    @Autowired
    private Converter converter;
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
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepo.countByBrandContainingAndNameContaining(brand,name);
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto= converter.convertToDto(product,ProductDto.class);
        Optional<Category> category=categoryRepo.findById(product.getCategoryId());
        if(category.isEmpty()){
            throw new ResourceNotFoundException("Category Not Found");
        }
        productDto.setCategory(category.get().getName());
        productDto.setImages(imageRepo.findByProductId(product.getId()).stream().map(image->converter.convertToDto(image,ImageDto.class)).toList());
        return productDto;
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Product> searchKey(Map<String, Object> params) {
        if(ObjectUtils.isEmpty(params.get("searchKey"))) return productRepo.findAll();
        Object searchKey=params.get("searchKey");
        SpecificationBuilder<Product> specificationBuilder=new SpecificationBuilder<>();
        Specification<Product> spec=null;
        Specification<Product> spec2;
        if(searchKey.toString().matches("\\d+")){
             spec= specificationBuilder.equal("price",searchKey)
                     .or(specificationBuilder.equal("inventory",searchKey))
                    .or(specificationBuilder.equal("id",searchKey));
        }
        spec2=specificationBuilder.contains("name",searchKey)
                                   .or(specificationBuilder.contains("brand",searchKey))
                                   .or(specificationBuilder.contains("description",searchKey));
        spec=(spec!=null)?spec.or(spec2):spec2;
        return productRepo.findAll(spec);
    }
}
