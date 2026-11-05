package com.ad.meeting.service;

import com.ad.meeting.dto.BookingRequest;
import com.ad.meeting.exception.BookingConflictException;
import com.ad.meeting.exception.InvalidBookingException;
import com.ad.meeting.exception.RoomNotFoundException;
import com.ad.meeting.model.Room;
import com.ad.meeting.repository.BookingRepository;
import com.ad.meeting.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;


    Room room01;
    BookingService service;
    Clock clock;

    @BeforeEach
    void initializingData() {
        Instant instant = Instant.parse("2026-10-29T10:00:00Z");
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        clock = Clock.fixed(instant, zoneId);

        service = new BookingService(bookingRepository, roomRepository, clock);

        room01 = Room.builder()
                .id(1L)
                .name("103")
                .capacity(12)
                .floor("RDC")
                .build();
    }

    @Test
    void testStartAfterEnd() {

        InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> service.createBooking(constructBookingRequest(LocalDateTime.parse("2026-12-12T11:00:00"), LocalDateTime.parse("2026-12-12T10:00:00"))));

        verifyNoInteractions(bookingRepository, roomRepository);
        assertEquals("The start of the booking is after the end", ex.getMessage());
    }

    @Test
    void testStartEqualEnd() {

        InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> service.createBooking(constructBookingRequest(LocalDateTime.parse("2026-12-12T10:00:00"), LocalDateTime.parse("2026-12-12T10:00:00"))));

        verifyNoInteractions(bookingRepository, roomRepository);
        assertEquals("The start and the end of the booking are the same", ex.getMessage());


    }

    @Test
    void testDurationOver4() {

        InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> service.createBooking(constructBookingRequest(LocalDateTime.parse("2026-12-12T11:00:00"), LocalDateTime.parse("2026-12-12T15:01:00"))));

        verifyNoInteractions(bookingRepository, roomRepository);
        assertEquals("The booking can't last more than 4 hours", ex.getMessage());


    }

    @Test
    void testBookingInThePast() {

        InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> service.createBooking(constructBookingRequest(LocalDateTime.parse("2026-10-29T08:00:00"), LocalDateTime.parse("2026-10-29T10:00:00"))));

        verifyNoInteractions(bookingRepository, roomRepository);
        assertEquals("The booking is scheduled in the past", ex.getMessage());


    }

    @Test
    void testRoomNotFound() {
        RoomNotFoundException ex = assertThrows(RoomNotFoundException.class, () -> service.createBooking(new BookingRequest("test", "John Doe", 2L, LocalDateTime.parse("2026-11-29T08:00:00"), LocalDateTime.parse("2026-11-29T10:00:00"))));

        verifyNoInteractions(bookingRepository);
        assertEquals("The room 2 has not been found", ex.getMessage());

    }

    @Test
    void testBookingConflict() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room01));
        when(bookingRepository.existsByRoomIdAndEndTimeGreaterThanAndStartTimeLessThan(1L, LocalDateTime.parse("2026-11-29T09:00:00"), LocalDateTime.parse("2026-11-29T11:00:00"))).thenReturn(true);
        BookingConflictException ex = assertThrows(BookingConflictException.class, () -> service.createBooking(constructBookingRequest(LocalDateTime.parse("2026-11-29T09:00:00"), LocalDateTime.parse("2026-11-29T11:00:00"))));

        verify(bookingRepository, never()).save(any());
        assertEquals("The booking is overlapping on an existing booking", ex.getMessage());

    }

    @Test
    void testBookingCreated() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room01));
        assert(service.createBooking(constructBookingRequest(LocalDateTime.parse("2026-11-29T09:00:00"), LocalDateTime.parse("2026-11-29T11:00:00")bookingRepository.findByRoomId(room01.getId()));

    }

    // Private

    private BookingRequest constructBookingRequest(LocalDateTime startTime, LocalDateTime endTime) {
        return new BookingRequest("Test", "John Doe", 1L, startTime, endTime);
    }


}
