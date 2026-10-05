package com.ad.meeting.dto;

import java.time.LocalDateTime;

public record ErrorResponse(String message, int errorCode, LocalDateTime timestamp) {
}
