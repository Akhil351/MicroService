package org.Akhil.COMMON_SERVICE.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;// number of product remaining in the stock
    private String description;
    private String category;
    private List<ImageDto> images;
}
