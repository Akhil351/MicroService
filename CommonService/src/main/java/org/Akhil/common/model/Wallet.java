package org.Akhil.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "wallets")
public class Wallet {
    private String walletId;
    @Builder.Default
    private BigDecimal tokens=BigDecimal.ZERO;
}
