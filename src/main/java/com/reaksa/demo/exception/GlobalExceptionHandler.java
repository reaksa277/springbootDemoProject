package com.reaksa.demo.exception;

import com.reaksa.demo.exception.model.CustomAuthenticationException;
import com.reaksa.demo.exception.model.DuplicateResourceException;
import com.reaksa.demo.exception.model.ResourceNotFoundException;
import com.reaksa.demo.exception.model.UnprocessableEntityException;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponseModel> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponseModel("fail", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<BaseResponseModel> handleDuplicateResourceException(DuplicateResourceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new BaseResponseModel("fail", ex.getMessage()));
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<BaseResponseModel> handleUnprocessableEntityException(UnprocessableEntityException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new BaseResponseModel("fail", ex.getMessage()));
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<BaseResponseModel> handleCustomAuthenticationException(CustomAuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new BaseResponseModel("fail", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseModel> handleGenericException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponseModel("fail", "unexpected error occured : " + ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseWithDataModel> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fileName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            // insert into error map
            errors.put(fileName,message);
        });


        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponseWithDataModel("fail", "validation fail", errors));
    }
}
