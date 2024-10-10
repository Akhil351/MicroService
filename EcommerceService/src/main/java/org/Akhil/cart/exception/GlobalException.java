package org.Akhil.cart.exception;

import feign.FeignException;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiResponse> feignClient(FeignException exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException exception){
        return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> internalServerError(Exception exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }
}
