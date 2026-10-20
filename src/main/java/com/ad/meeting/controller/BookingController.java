package com.ad.meeting.controller;

import com.ad.meeting.dto.BookingResponse;
import com.ad.meeting.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookings() {
        List<BookingResponse> bookingsRead = bookingService.getBookings();
        return ResponseEntity.ok(bookingsRead);
    }
}
