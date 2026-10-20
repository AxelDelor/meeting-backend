package com.ad.meeting.dto;

import com.ad.meeting.model.Room;

import java.time.LocalDateTime;

public record BookingResponse(
        Long id,
        Long roomId,
        String title,
        String Organizer,
        LocalDateTime startTime,
        LocalDateTime endTime
) {

}
