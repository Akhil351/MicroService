package org.Akhil.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private Long productId;
    private Integer quantity;
    @Builder.Default
    private BigDecimal unitPrice=BigDecimal.ZERO;
    private BigDecimal totalPrice;
    public void setTotalPrice(){
        this.totalPrice=this.unitPrice.multiply(new BigDecimal(this.quantity));
    }
}
