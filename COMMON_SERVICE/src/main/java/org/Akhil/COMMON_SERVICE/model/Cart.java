package org.Akhil.COMMON_SERVICE.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder.Default
    private BigDecimal totalAmount=BigDecimal.ZERO;
    @ElementCollection
    @CollectionTable(name="items",joinColumns = @JoinColumn(name="cart_id"))
    private Set<Long> cartItemIds;
}
