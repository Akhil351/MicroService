package org.Akhil.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {
    @Id
    private String id;
    private String name;
    private String brand;
    private String image;
    private BigDecimal price;
    private int inventory;// number of product remaining in the stock
    private String description;
    private String categoryId;
}
