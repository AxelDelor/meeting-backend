package com.ad.meeting.dto;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        String message,
        Integer status,
        Instant timestamp,
        List<FieldValidationError> errors
) {
}
