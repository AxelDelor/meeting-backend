package com.ad.meeting.exception;

public class BookingConflictException extends MeetingException {
    public BookingConflictException(String message) {
        super(message);
    }
}
