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
public class OrderItem {
    @Id
    private String id;
    private String orderId;
    private String productId;
    private Integer quantity;
    @Builder.Default
    private BigDecimal unitPrice=BigDecimal.ZERO;
    private BigDecimal totalPrice;
    public void setTotalPrice(){
        this.totalPrice=this.unitPrice.multiply(new BigDecimal(this.quantity));
    }
}
