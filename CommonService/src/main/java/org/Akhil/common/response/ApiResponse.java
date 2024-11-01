package org.Akhil.common.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
@Data
@Builder
public class ApiResponse {
    private String status;
    @Builder.Default
    private LocalDate timeStamp=LocalDate.now();
    private Object data;
    private Object error;

}
