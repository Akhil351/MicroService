package org.Akhil.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
    private String id;
    private String productName;
    private int quantity;
    private String productImageUrl;
    @Builder.Default
    private BigDecimal unitPrice=BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal totalPrice=BigDecimal.ZERO;
}
