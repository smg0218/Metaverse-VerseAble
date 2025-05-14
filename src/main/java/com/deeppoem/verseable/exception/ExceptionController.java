package com.deeppoem.verseable.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException e) {
        Map<String,String> responseBody = new HashMap<>();
        responseBody.put("message", e.getMessage());

        return ResponseEntity.badRequest().body(responseBody);
    }
}
