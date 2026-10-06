package com.ad.meeting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record BookingRequest(

        @Size(max = 60, message = "The title of the booking must not exceed 60 characters")
        @NotBlank(message = "The booking must have a title")
        String title,

        @Size(max = 60, message = "The name of the organizer must not exceed 60 characters")
        @NotBlank(message = "The booking must have an organizer")
        String organizer,

        @NotNull(message = "The booking must have a room")
        Long roomId,

        @NotNull(message = "The booking must have a start time")
        LocalDateTime startTime,

        @NotNull(message = "The booking must have an end time")
        LocalDateTime endTime

) {
}
