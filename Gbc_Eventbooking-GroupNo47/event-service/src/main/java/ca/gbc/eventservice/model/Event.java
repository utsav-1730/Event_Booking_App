package ca.gbc.eventservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "events")
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    private String id;
    private String eventName;
    private String organizerId;
    private String roomId;
    private String eventType;
    private int expectedAttendees;
    @Builder.Default
    private String status = "processing";
}
