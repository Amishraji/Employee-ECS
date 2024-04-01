package com.pranshu.crudproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class EmployeeNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", exception.getMessage());
        return errorResponse;
    }
    
//    @ResponseBody
//    @ExceptionHandler(EmployeeNotFoundException.class)
//    public ResponseEntity<String> handleEmployeeNotFoundExceptionResponseEntity(EmployeeNotFoundException exception) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
//    }
//    public class SecretsRetrievalException extends RuntimeException {
//        public SecretsRetrievalException(String message) {
//            super(message);
//        }
//    }
}
