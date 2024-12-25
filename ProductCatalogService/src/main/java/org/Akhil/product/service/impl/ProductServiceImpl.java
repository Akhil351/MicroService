package org.Akhil.product.service.impl;

import org.Akhil.common.dto.ImageDto;
import org.Akhil.common.dto.ProductDto;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.mapper.Converter;
import org.Akhil.common.model.Category;
import org.Akhil.common.model.OrderItem;
import org.Akhil.common.model.Product;
import org.Akhil.common.repo.CategoryRepo;
import org.Akhil.common.repo.ImageRepo;
import org.Akhil.common.repo.ProductRepo;
import org.Akhil.common.specification.SpecificationBuilder;
import org.Akhil.common.util.Utils;
import org.Akhil.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    private static final Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Product addProduct(ProductDto productDto) {
        Category category=this.getCategory(productDto.getCategory());
        return productRepo.save(this.createProduct(productDto,category.getId()));
    }
    private Category getCategory(String categoryName){
        return  categoryRepo.findByName(categoryName)
                .orElseGet(()->{
                    Category newCategory=Category.builder().id("cat"+ UUID.randomUUID().toString()).name(categoryName).build();
                    return categoryRepo.save(newCategory);
                });
    }
    private Product createProduct(ProductDto productDto,String categoryId){
        return Product.builder()
                .id("pro"+UUID.randomUUID().toString())
                .name(productDto.getName())
                .brand(productDto.getBrand())
                .price(productDto.getPrice())
                .image(productDto.getImage())
                .inventory(productDto.getInventory())
                .description(productDto.getDescription())
                .categoryId(categoryId)
                .build();
    }

    @Override
    public Product getProductById(String id) {
        return productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Product Not Found"));
    }

    @Override
    public void deleteProductById(String id) {
        productRepo.findById(id).ifPresentOrElse(productRepo::delete,()->{throw new ResourceNotFoundException("Product Not Found");});
        imageRepo.deleteAllByProductId(id);
    }

    @Override
    public Product updateProduct(ProductDto productDto, String productId) {
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
        if(!ObjectUtils.isEmpty(productDto.getImage())) existingProduct.setImage(productDto.getImage());
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
        int pageNo=(ObjectUtils.isEmpty(params.get(Utils.PAGE_NO)))?0:Integer.parseInt(params.get(Utils.PAGE_NO).toString());
        int pageSize=(ObjectUtils.isEmpty(params.get(Utils.PAGE_SIZE)))?5:Integer.parseInt(params.get(Utils.PAGE_SIZE).toString());
        Sort sort=Sort.by(Sort.Order.asc("name"));
        PageRequest pageRequest=PageRequest.of(pageNo,pageSize,sort);
        if(ObjectUtils.isEmpty(params.get(Utils.SEARCH_KEY))){
            return productRepo.findAll(pageRequest).getContent();
        }
        Object searchKey=params.get(Utils.SEARCH_KEY);
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
        return productRepo.findAll(spec,pageRequest).getContent();
    }

    @KafkaListener(topics = "${spring.kafka.topic1.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void updateQuantity(OrderItem orderItem){
        logger.info("quantity updated");
        Product product=this.getProductById(orderItem.getProductId());
        product.setInventory(product.getInventory()-orderItem.getQuantity());
        productRepo.save(product);
    }


}
