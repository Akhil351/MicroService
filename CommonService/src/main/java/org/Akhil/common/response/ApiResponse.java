package org.Akhil.common.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse {
    private String status;
    @Builder.Default
    private LocalDateTime timeStamp=LocalDateTime.now();
    private Object data;
    private Object error;

}
