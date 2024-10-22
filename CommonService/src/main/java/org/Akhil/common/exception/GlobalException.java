package org.Akhil.common.exception;

import feign.FeignException;
import org.Akhil.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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

    @ExceptionHandler(TokenInvalid.class)
    public ResponseEntity<ApiResponse> tokenInvalid(TokenInvalid exception){
        return ResponseEntity.status(UNAUTHORIZED).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> internalServerError(Exception exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse> authorizationDeniedException(AuthorizationDeniedException exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<ApiResponse> userAlreadyExist(UserAlreadyExist exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }

    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<ApiResponse> invalidCredential(InvalidCredential exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiResponse> feignClient(FeignException exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity<ApiResponse> cartException(CartException exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Error").data(exception.getMessage()).build());
    }
}
