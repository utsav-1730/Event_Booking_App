package ca.gbc.eventservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEvent {
    private String bookingId;
    private String useremail;
    private String roomId;
    private String bookingDate;
}

