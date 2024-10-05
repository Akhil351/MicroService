package org.Akhil.product.exception;

import feign.FeignException;
import org.Akhil.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiResponse> feignClient(FeignException exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }
}
