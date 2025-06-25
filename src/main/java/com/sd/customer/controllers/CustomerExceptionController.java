package com.sd.customer.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice(assignableTypes = {CustomerController.class})
public class CustomerExceptionController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerExceptionController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        LOGGER.error(ex);
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", 400);
        errorBody.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorBody.put("message", Arrays.asList(ex.getMessage()));
        errorBody.put("type", ex.getClass());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorBody);
    }
}
