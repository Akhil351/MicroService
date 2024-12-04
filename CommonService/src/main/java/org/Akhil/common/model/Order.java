package org.Akhil.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class Order {
    @Id
    private String orderId;
    private LocalDateTime orderDate;
    @Builder.Default
    private BigDecimal totalAmount=BigDecimal.ZERO;
    private Integer orderStatus;
    private String userId;
}
