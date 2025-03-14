package ca.gbc.roomservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomID;
    @Column(name = "roomname", nullable = false)
    private String roomName;
    private Integer capacity;
    @ElementCollection
    @CollectionTable(name = "room_features", joinColumns = @JoinColumn(name = "roomid"))
    @Column(name = "features")
    private List<String> features;
    private boolean availability;
}
