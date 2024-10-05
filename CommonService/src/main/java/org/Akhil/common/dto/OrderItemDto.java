package org.Akhil.common.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDto {
    private Long id;
    private String productName;
    private int quantity;
    @Builder.Default
    private BigDecimal unitPrice=BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal totalPrice=BigDecimal.ZERO;
}
