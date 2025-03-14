package ca.gbc.eventservice.controller;

import ca.gbc.eventservice.client.RoomClient;
import ca.gbc.eventservice.client.UserClient;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.service.EventService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;
    private final UserClient userClient;
    private final RoomClient roomClient;

    // Endpoint to create or update an event
    @PostMapping
    public ResponseEntity<Event> createOrUpdateEvent(@RequestBody Event event) {

        if(!userClient.isValid(event.getOrganizerId())) {
            throw new RuntimeException("User id is invalid");
        }

        if(!roomClient.isAvailable(event.getRoomId())) {
            throw new RuntimeException("Room no " + event.getRoomId() + " is not available");
        }

        Event savedEvent = eventService.createOrUpdateEvent(event);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Endpoint to get events by name
    @GetMapping("/search")
    public ResponseEntity<List<Event>> getEventByName(@RequestParam("name") String eventName) {
        List<Event> events = eventService.getEventByName(eventName);
        if (events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Endpoint to delete an event by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") String eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/isEventValid")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> isEventValid(@RequestParam String eventId) {
        Optional<Event> eventOp = eventService.getEventById(eventId);
        return new ResponseEntity<>(eventOp.isPresent(), HttpStatus.OK);
    }


    @PostMapping("/updateEventStatus")
    public ResponseEntity<Void> isEventValid(@RequestParam String eventId, @RequestParam String status) {
        Optional<Event> eventOp = eventService.getEventById(eventId);
        Event event = eventOp.get();
        event.setStatus(status);
        eventService.createOrUpdateEvent(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}