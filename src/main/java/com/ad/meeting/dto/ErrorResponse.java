package com.ad.meeting.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record ErrorResponse(
        String message,
        Integer errorNumber,
        Instant timestamp
) {
}
