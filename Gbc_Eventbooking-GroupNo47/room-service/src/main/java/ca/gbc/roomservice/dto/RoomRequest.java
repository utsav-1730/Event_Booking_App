package ca.gbc.roomservice.dto;

import java.util.List;

public record RoomRequest(Long roomId, String roomName,
                          Integer capacity, List<String> features,
                          boolean availability) { }