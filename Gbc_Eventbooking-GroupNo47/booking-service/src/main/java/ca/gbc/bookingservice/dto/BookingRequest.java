package ca.gbc.bookingservice.dto;

import java.time.Instant;

public record BookingRequest(
        String roomId,
        String userId,
        Instant startTime,
        Instant endTime,
        String useremail
) {}