package ca.gbc.bookingservice.dto;
import java.time.Instant;

public record BookingResponse(
        String id,
        String roomId,
        String userId,
        Instant startTime,
        Instant endTime
) {}
