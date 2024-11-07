package org.Akhil.common.exception;

import feign.FeignException;
import org.Akhil.common.enums.ErrorCode;
import org.Akhil.common.response.ApiResponse;
import org.Akhil.common.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ApiResponse> emailAlreadyExist(AlreadyExistException exception){
        return ResponseEntity.status(CONFLICT).body(ApiResponse.builder().status("Failed").data(null).error(ErrorResponse.builder().errorCode(ErrorCode.CONFLICT.getErrorCode()).errorDescription(exception.getMessage()).build()).build());
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException exception){
        return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().status("Failed").data(null).error(ErrorResponse.builder().errorCode(ErrorCode.NOT_FOUND.getErrorCode()).errorDescription(exception.getMessage()).build()).build());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> usernameNotFound(UsernameNotFoundException exception){
        return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().status("Failed").data(null).error(ErrorResponse.builder().errorCode(ErrorCode.NOT_FOUND.getErrorCode()).errorDescription(exception.getMessage()).build()).build());
    }

    @ExceptionHandler(TokenInvalid.class)
    public ResponseEntity<ApiResponse> tokenInvalid(TokenInvalid exception){
        return ResponseEntity.status(UNAUTHORIZED).body(ApiResponse.builder().status("Failed").data(null).error(ErrorResponse.builder().errorCode(ErrorCode.UNAUTHORIZED.getErrorCode()).errorDescription(exception.getMessage()).build()).build());
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse> authorizationDeniedException(AuthorizationDeniedException exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().status("Failed").data(null).error(ErrorResponse.builder().errorCode(ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode()).errorDescription(exception.getMessage()).build()).build());
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<ApiResponse> userAlreadyExist(UserAlreadyExist exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().status("Failed").data(null).error(ErrorResponse.builder().errorCode(ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode()).errorDescription(exception.getMessage()).build()).build());
    }

    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<ApiResponse> invalidCredential(InvalidCredential exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().status("Failed").data(null).error(ErrorResponse.builder().errorCode(ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode()).errorDescription(exception.getMessage()).build()).build());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiResponse> feignClient(FeignException exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().status("Failed").data(null).error(ErrorResponse.builder().errorCode(ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode()).errorDescription(exception.getMessage()).build()).build());
    }

    @ExceptionHandler(InSufficientException.class)
    public ResponseEntity<ApiResponse> outOfStock(InSufficientException exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().status("Failed").data(null).error(ErrorResponse.builder().errorCode(ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode()).errorDescription(exception.getMessage()).build()).build());
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity<ApiResponse> cartException(CartException exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().status("Failed").data(null).error(ErrorResponse.builder().errorCode(ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode()).errorDescription(exception.getMessage()).build()).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String,String> errors=new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError)error).getField();
            String errorMessage=error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return ResponseEntity.status(BAD_REQUEST).body(ApiResponse.builder().status("Error").error(ErrorResponse.builder().errorCode(ErrorCode.BAD_REQUEST.getErrorCode()).errorDescription(errors).build()).build());
    }
}
