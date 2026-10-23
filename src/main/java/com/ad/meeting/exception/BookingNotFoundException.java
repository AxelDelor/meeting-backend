package com.ad.meeting.exception;

public class BookingNotFoundException extends MeetingException {
    public BookingNotFoundException(Long bookingId) {
        super("The booking " + bookingId + " has not been found");
    }
}
