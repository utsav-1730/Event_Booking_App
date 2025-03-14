package ca.gbc.eventservice.service;


import ca.gbc.eventservice.event.BookingEvent;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {

    private final JavaMailSender javaMailSender;
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository, JavaMailSender javaMailSender) {
        this.eventRepository = eventRepository;
        this.javaMailSender = javaMailSender;
    }

    // Create or update event
    public Optional<Event> getEventById(String eventId) { return eventRepository.findById(eventId); }
    public Event createOrUpdateEvent(Event event) {
        return eventRepository.save(event);
    }

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get event by name
    public List<Event> getEventByName(String eventName) {
        return eventRepository.findByEventName(eventName);
    }

    // Delete event by id
    public void deleteEvent(String eventId) {
        eventRepository.deleteById(eventId);
    }
    @KafkaListener(topics = "booking-done")
    private void listen(BookingEvent orderPlacedEvent){
        log.info("Received message from booking-done topic {}", orderPlacedEvent);
        //send email to customer
        MimeMessagePreparator messagePreparator= mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("comp3095@georgebrown.ca");
            messageHelper.setTo(orderPlacedEvent.getUseremail());
            messageHelper.setSubject(String.format("Your Booking (%s) was confirmed successfully",
                    orderPlacedEvent.getBookingId()));
            messageHelper.setText(String.format("""
                    
                    Good Day
                  
                    Your Booking with booking id (%s) was confirmed successfully for room id (%s)
                    for (%s)
                    
                    Thank you for your booking enjoy
                    Gbc Librray Staff subhan Mohammed abdul
                    """,orderPlacedEvent.getBookingId(),orderPlacedEvent.getRoomId(),orderPlacedEvent.getBookingDate()));
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("order notification send successfully");
        }catch (MailException e){
            log.error("exeception occured when sending the email", e);
            throw new RuntimeException("exception occured when attempting to send mail",e);
        }


    }
}
