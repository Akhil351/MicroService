package org.Akhil.common.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private String orderId;
    private String DateOfOrder;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDto> orderItems;

}
