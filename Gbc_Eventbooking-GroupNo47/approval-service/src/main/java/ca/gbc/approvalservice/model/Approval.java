package ca.gbc.approvalservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "Approval")
public class Approval {
    @Id
    private String id;
    private String eventId;
    private String approvedId;
    private boolean approved;
    private LocalDateTime approvalDate;
}
