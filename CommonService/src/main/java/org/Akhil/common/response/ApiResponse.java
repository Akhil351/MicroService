package org.Akhil.common.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    String message;
    Object data;
}