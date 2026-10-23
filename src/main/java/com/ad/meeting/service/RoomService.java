package com.ad.meeting.service;

import com.ad.meeting.dto.RoomRequest;
import com.ad.meeting.dto.RoomResponse;
import com.ad.meeting.exception.RoomNotFoundException;
import com.ad.meeting.model.Room;
import com.ad.meeting.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return roomToResponse(findRoomById(roomId));
    }

    @Transactional
    public RoomResponse addRoom(RoomRequest roomRequest) {
        Room room = requestToRoom(roomRequest);
        return roomToResponse(roomRepository.save(room));
    }

    @Transactional
    public RoomResponse updateRoom(Long roomId, RoomRequest roomRequest) {
        Room room = findRoomById(roomId);
        room.update(roomRequest.name(), roomRequest.capacity(), roomRequest.floor());
        return roomToResponse(room);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        roomRepository.delete(findRoomById(roomId));
    }

    // Private

    private RoomResponse roomToResponse(Room room) {
        return new RoomResponse(room.getId(), room.getName(), room.getCapacity(), room.getFloor());
    }

    private Room requestToRoom(RoomRequest roomRequest) {
        return Room.builder()
                .name(roomRequest.name())
                .capacity(roomRequest.capacity())
                .floor(roomRequest.floor())
                .build();
    }

    private Room findRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
    }


}
