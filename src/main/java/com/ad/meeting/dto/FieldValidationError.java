package com.ad.meeting.dto;

public record FieldValidationError(
        String field,
        String message
) {
    
}
