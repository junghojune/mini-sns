package com.hosu.sns.exception;

import com.hosu.sns.controller.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.hosu.sns.exception.ErrorCode.DATABASE_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

//    @ExceptionHandler(SnsApplicationException.class)
//    public ResponseEntity<?> applicationHandler(SnsApplicationException e){
//        log.error("Error Code {}", e.toString());
//        return ResponseEntity.status(e.getErrorCode().getStatus())
//                .body(e.getErrorCode().name());
//    }

    @ExceptionHandler(SnsApplicationException.class)
    public ResponseEntity<?> errorHandler(SnsApplicationException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().name()));
    }

//    @ExceptionHandler(SnsApplicationException.class)
//    public ResponseEntity<?> applicationHandler(RuntimeException e){
//        log.error("Error Code {}", e.toString());
//        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
//                .body(INTERNAL_SERVER_ERROR.name());
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> databaseErrorHandler(IllegalArgumentException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(DATABASE_ERROR.getStatus())
                .body(Response.error(DATABASE_ERROR.name()));
    }
}
