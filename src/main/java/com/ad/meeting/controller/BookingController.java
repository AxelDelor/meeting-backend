package com.ad.meeting.controller;

import com.ad.meeting.dto.BookingResponse;
import com.ad.meeting.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{roomId}")
    public ResponseEntity<List<BookingResponse>> getBookings(@PathVariable Long roomId) {
        List<BookingResponse> bookingsRead = bookingService.getRoomBookings(roomId);
        return ResponseEntity.ok(bookingsRead);
    }
}
