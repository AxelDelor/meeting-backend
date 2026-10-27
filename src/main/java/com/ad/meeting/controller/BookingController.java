package com.ad.meeting.controller;

import com.ad.meeting.dto.BookingRequest;
import com.ad.meeting.dto.BookingResponse;
import com.ad.meeting.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookings() {
        return ResponseEntity.ok(bookingService.getBookings());
    }

    @GetMapping(params = "roomId")
    public ResponseEntity<List<BookingResponse>> getRoomBookings(@RequestParam Long roomId) {
        return ResponseEntity.ok(bookingService.getRoomBookings(roomId));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBooking(bookingId));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBookingById(bookingId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        BookingResponse bookingCreated = bookingService.createBooking(bookingRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{bookingId}")
                .buildAndExpand(bookingCreated.id())
                .toUri();

        return ResponseEntity.created(location).body(bookingCreated);

    }

}
