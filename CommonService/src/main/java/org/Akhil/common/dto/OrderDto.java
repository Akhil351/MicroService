package org.Akhil.common.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private String orderId;
    private LocalDate orderDate;
    @Builder.Default
    private BigDecimal totalAmount=BigDecimal.ZERO;
    private String status;
    private List<OrderItemDto> orderItems;

}
