package com.hosu.sns.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(SnsApplicationException.class)
    public ResponseEntity<?> applicationHandler(SnsApplicationException e){
        log.error("Error Code {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(e.getErrorCode().name());
    }
}
