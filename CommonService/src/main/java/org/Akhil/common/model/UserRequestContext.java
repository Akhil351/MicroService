package org.Akhil.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestContext {
    private String userId;
    private String userEmail;
    private List<String> authorities;
}
