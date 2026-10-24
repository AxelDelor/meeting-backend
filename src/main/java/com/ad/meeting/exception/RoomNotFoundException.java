package com.ad.meeting.exception;

public class RoomNotFoundException extends MeetingException {
    public RoomNotFoundException(Long roomId) {
        super("The room " + roomId + " has not been found");
    }
}
