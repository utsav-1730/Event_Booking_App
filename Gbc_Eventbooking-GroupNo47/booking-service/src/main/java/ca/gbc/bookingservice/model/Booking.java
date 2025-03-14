package ca.gbc.bookingservice.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.Instant;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "bookings")
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    private String id;

    @Field("room_id")
    private String roomId;

    @Field("user_id")
    private String userId;

    @Field("start_time")
    private Instant startTime;

    @Field("end_time")
    private Instant endTime;
}