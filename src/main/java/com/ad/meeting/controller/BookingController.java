package com.ad.meeting.controller;

import com.ad.meeting.dto.BookingResponse;
import com.ad.meeting.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms/{roomId}/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping()
    public ResponseEntity<List<BookingResponse>> getBookings(@PathVariable Long roomId) {
        List<BookingResponse> bookingsRead = bookingService.getRoomBookings(roomId);
        return ResponseEntity.ok(bookingsRead);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long roomId, Long bookingId) {
        bookingService.deleteBookingById(bookingId);
        return ResponseEntity.noContent().build();
    }
}
