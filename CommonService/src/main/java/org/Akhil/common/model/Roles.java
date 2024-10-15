package org.Akhil.common.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "roles")
@Builder
public class Roles {
    @Id
    private String id;
    private Integer role;
    private String userId;
}
