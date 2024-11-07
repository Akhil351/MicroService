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
@Entity
@Builder
public class CartItem {
    @Id
    private String id;
    private Integer quantity;
    @Builder.Default
    private BigDecimal unitPrice=BigDecimal.ZERO;
    private BigDecimal totalPrice;
    private String productId;
    private String cartId;
    public void setTotalPrice(){
        this.totalPrice=unitPrice.multiply(new BigDecimal(quantity));
    }
}
