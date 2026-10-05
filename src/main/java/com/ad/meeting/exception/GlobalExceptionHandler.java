package com.ad.meeting.exception;

import com.ad.meeting.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRoomException.class)
    public ResponseEntity<ErrorResponse> handleConflict(MeetingException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), 409, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


}
