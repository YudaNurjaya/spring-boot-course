package com.example.demospringboot.controller;

import com.example.demospringboot.exception.DataEmptyException;
import com.example.demospringboot.exception.DuplicateException;
import com.example.demospringboot.exception.FailedToRunException;
import com.example.demospringboot.exception.NotFoundException;
import com.example.demospringboot.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new com.example.demospringboot.model.response.ErrorResponse(exception.getMessage(),"00"));
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> HandleDataNotFoundException(NotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage(),"00"));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodeValidException(MethodArgumentNotValidException exception){
        List<FieldError> fieldErrorList = exception.getBindingResult().getFieldErrors();
        List<String> errors= new ArrayList<>();
        for(FieldError fieldError: fieldErrorList){
            errors.add(fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors.toString(),"400"));
    }
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> HandleDuplicateException(DuplicateException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage(),"400"));
    }
    @ExceptionHandler(DataEmptyException.class)
    public ResponseEntity<ErrorResponse> HandleDataEmptyException(DataEmptyException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage(), "400"));
    }
    @ExceptionHandler(FailedToRunException.class)
    public ResponseEntity<ErrorResponse> HandleFailedToRun(FailedToRunException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage(),"400"));
    }
}
