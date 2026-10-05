package com.ad.meeting.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoomRequest(
        @NotBlank(message = "The room must have a name")
        @Size(max = 60, message = "The name of the room must not exceed 60 characters")
        String name,

        @NotNull(message = "The room must have a capacity")
        @Min(value = 1, message = "The capacity of the room must be at least 1")
        Integer capacity,

        @NotBlank(message = "The floor must have a name")
        @Size(max = 60, message = "The name of the floor must not exceed 60 characters")
        String floor) {

}
