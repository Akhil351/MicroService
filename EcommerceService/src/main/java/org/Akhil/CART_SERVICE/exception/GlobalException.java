package org.Akhil.CART_SERVICE.exception;

import feign.FeignException;
import org.Akhil.COMMON_SERVICE.dto.ApiResponse;
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
