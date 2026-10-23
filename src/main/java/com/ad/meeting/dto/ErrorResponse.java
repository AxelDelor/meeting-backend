package com.ad.meeting.dto;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        Integer status,
        String message,
        Instant timestamp,
        List<FieldValidationError> errors
) {
}
