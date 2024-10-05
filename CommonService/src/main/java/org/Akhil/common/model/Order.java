package org.Akhil.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Akhil.common.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class Order {
    @Id
    private String orderId;
    private LocalDate orderDate;
    @Builder.Default
    private BigDecimal totalAmount=BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String userId;
}
