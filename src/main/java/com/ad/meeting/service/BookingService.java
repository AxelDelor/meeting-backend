package com.ad.meeting.service;

import com.ad.meeting.dto.BookingResponse;
import com.ad.meeting.model.Booking;
import com.ad.meeting.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public List<BookingResponse> getBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream()
                .map(this::bookingToResponse)
                .toList();
    }

    // Private

    private BookingResponse bookingToResponse(Booking booking) {
        return new BookingResponse(booking.getId(), booking.getRoom().getId(), booking.getTitle(), booking.getOrganizer(), booking.getStartTime(), booking.getEndTime());
    }
}
