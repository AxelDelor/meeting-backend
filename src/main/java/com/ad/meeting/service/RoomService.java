package com.ad.meeting.service;

import com.ad.meeting.dto.RoomResponse;
import com.ad.meeting.exception.RoomNotFoundException;
import com.ad.meeting.model.Room;
import com.ad.meeting.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomResponse> getRooms() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream()
                .map(this::roomToResponse)
                .toList();
    }


    public RoomResponse getRoom(Long roomId) {

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("the room " + roomId + " has not been found"));

        return roomToResponse(room);

    }

    private RoomResponse roomToResponse(Room room) {
        return new RoomResponse(room.getId(), room.getName(), room.getCapacity(), room.getFloor());
    }

}
