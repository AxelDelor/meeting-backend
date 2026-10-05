package com.ad.meeting.dto;

public record BookingRequest(
        Long roomId,
        String title,
        String organizer

) {
}
