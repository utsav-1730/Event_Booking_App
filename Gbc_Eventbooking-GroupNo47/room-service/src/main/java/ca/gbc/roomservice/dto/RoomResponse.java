package ca.gbc.roomservice.dto;

import java.util.List;

public record RoomResponse(Long roomId, String roomName,
                           Integer capacity, List<String> features,
                           boolean availability) {
}
