package com.ad.meeting.service;

import com.ad.meeting.dto.BookingRequest;
import com.ad.meeting.dto.BookingResponse;
import com.ad.meeting.exception.BookingConflictException;
import com.ad.meeting.exception.BookingNotFoundException;
import com.ad.meeting.exception.InvalidBookingException;
import com.ad.meeting.exception.RoomNotFoundException;
import com.ad.meeting.model.Booking;
import com.ad.meeting.model.Room;
import com.ad.meeting.repository.BookingRepository;
import com.ad.meeting.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final Clock clock;

    @Transactional(readOnly = true)
    public List<BookingResponse> getRoomBookings(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new RoomNotFoundException(roomId);
        }
        return bookingRepository.findByRoomId(roomId).stream().map(this::bookingToResponse).toList();
    }

    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        LocalDateTime now = LocalDateTime.now(clock);
        Long roomId = bookingRequest.roomId();
        LocalDateTime startTime = bookingRequest.startTime();
        LocalDateTime endTime = bookingRequest.endTime();

        if (startTime.isAfter(endTime)) {
            throw new InvalidBookingException("The start of the booking is after the end");
        }

        if (startTime.isEqual(endTime)) {
            throw new InvalidBookingException("The start and the end of the booking are the same");
        }


        if (Duration.ofHours(4).compareTo(Duration.between(startTime, endTime)) > 0) {
            throw new InvalidBookingException("The booking can't last more than 4 hours");
        }

        if (startTime.isBefore(now)) {
            throw new InvalidBookingException("The booking is scheduled in the past");
        }

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));

        if (bookingRepository.existsByRoomIdAndEndTimeGreaterThanAndStartTimeLessThan(roomId, startTime, endTime)) {
            throw new BookingConflictException("The booking is overlapping on an existing booking");
        }

        Booking booking = requestToBooking(bookingRequest, room);
        return bookingToResponse(bookingRepository.save(booking));
    }

    @Transactional
    public void deleteBookingById(Long bookingId) {
        bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));
    }

    // Private

    private BookingResponse bookingToResponse(Booking booking) {
        return new BookingResponse(booking.getId(), booking.getRoom().getId(), booking.getTitle(), booking.getOrganizer(), booking.getStartTime(), booking.getEndTime());
    }

    private Booking requestToBooking(BookingRequest bookingRequest, Room room) {
        return Booking.builder()
                .room(room)
                .title(bookingRequest.title())
                .organizer(bookingRequest.organizer())
                .startTime(bookingRequest.startTime())
                .endTime(bookingRequest.endTime())
                .build();
    }

}
