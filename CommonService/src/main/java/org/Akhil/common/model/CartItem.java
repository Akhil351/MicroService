package org.Akhil.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    @Builder.Default
    private BigDecimal unitPrice=BigDecimal.ZERO;
    private BigDecimal totalPrice;
    private Long productId;
    private Long cartId;
    public void setTotalPrice(){
        this.totalPrice=unitPrice.multiply(new BigDecimal(quantity));
    }
}
