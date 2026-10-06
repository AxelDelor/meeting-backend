package com.ad.meeting.exception;

import com.ad.meeting.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(MeetingException ex) {
        HttpStatus notFoundStatus = HttpStatus.NOT_FOUND;
        ErrorResponse error = new ErrorResponse(ex.getMessage(), notFoundStatus.value(), Instant.now());
        return ResponseEntity.status(notFoundStatus).body(error);
    }


}
