package org.Akhil.login.exception;


import org.Akhil.common.exception.AlreadyExistException;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ApiResponse> emailAlreadyExist(AlreadyExistException exception){
        return ResponseEntity.status(CONFLICT).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException exception){
        return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> usernameNotFound(UsernameNotFoundException exception){
        return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> internalServerError(Exception exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }
}
