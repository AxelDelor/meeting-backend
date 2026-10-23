package com.ad.meeting.service;

import com.ad.meeting.dto.BookingResponse;
import com.ad.meeting.exception.RoomNotFoundException;
import com.ad.meeting.model.Booking;
import com.ad.meeting.repository.BookingRepository;
import com.ad.meeting.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    
    @Transactional(readOnly = true)
    public List<BookingResponse> getRoomBookings(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new RoomNotFoundException(roomId);
        }
        return bookingRepository.findByRoomId(roomId).stream().map(this::bookingToResponse).toList();
    }

    // Private

    private BookingResponse bookingToResponse(Booking booking) {
        return new BookingResponse(booking.getId(), booking.getRoom().getId(), booking.getTitle(), booking.getOrganizer(), booking.getStartTime(), booking.getEndTime());
    }
}
