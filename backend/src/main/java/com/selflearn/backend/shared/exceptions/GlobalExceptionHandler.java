package com.selflearn.backend.shared.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName;
            try{
                fieldName = ((FieldError) error).getField();
            } catch (ClassCastException e){
                fieldName = error.getObjectName();
            }
            String errorMessage = error.getDefaultMessage();
            List<String> errorMessages = errors.get(fieldName);
            if (errorMessages != null) {
                errorMessages.add(errorMessage);
            }else{
                errorMessages = new ArrayList<>(){{
                    add(errorMessage);
                }};
                errors.put(fieldName, errorMessages);
            }
        });
        return new ResponseEntity<>(errors, headers, status);
    }
}
