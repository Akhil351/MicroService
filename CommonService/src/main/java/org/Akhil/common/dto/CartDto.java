package org.Akhil.common.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
@Data
public class CartDto {
    private String id;
    private BigDecimal totalAmount;
    private List<CartItemDto> cartItems;
}
