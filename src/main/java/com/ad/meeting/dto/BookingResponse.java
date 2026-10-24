package com.ad.meeting.dto;

import java.time.LocalDateTime;

public record BookingResponse(
        Long id,
        Long roomId,
        String title,
        String organizer,
        LocalDateTime startTime,
        LocalDateTime endTime
) {

}
