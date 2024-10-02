package org.Akhil.COMMON_SERVICE.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    String message;
    Object data;
}
