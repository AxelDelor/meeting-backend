package com.ad.meeting.dto;

public record RoomResponse(
        Long id,
        String name,
        Integer capacity,
        String floor
) {
}
