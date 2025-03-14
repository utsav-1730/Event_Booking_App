package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.event.BookingEvent;
import ca.gbc.bookingservice.model.Booking;
import ca.gbc.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final MongoTemplate mongoTemplate;
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        log.debug("Creating a new booking for room ID: {}", bookingRequest.roomId());

        // Rename 'booking' to 'newBooking' to avoid conflict
        Booking newBooking = Booking.builder()
                .roomId(bookingRequest.roomId())
                .userId(bookingRequest.userId())
                .startTime(bookingRequest.startTime())
                .endTime(bookingRequest.endTime())
                .build();

        // Persist the booking to the MongoDB database
        bookingRepository.save(newBooking);
        log.info("Booking {} is saved", newBooking.getId());

        BookingEvent bookingEvent= new BookingEvent(newBooking.getId(),bookingRequest.useremail(),newBooking.getRoomId()
        ,newBooking.getStartTime().toString());
        kafkaTemplate.send("booking-done", bookingEvent);

        // Return a response containing booking details
        return new BookingResponse(
                newBooking.getId(),
                newBooking.getRoomId(),
                newBooking.getUserId(),
                newBooking.getStartTime(),
                newBooking.getEndTime()
        );

    }

    @Override
    public Optional<Booking> getBookingById(String id) {
        log.debug("Retrieving booking with id: {}", id);

        // Use MongoTemplate to find the booking by ID
        Query query = new Query(Criteria.where("id").is(id));
        Booking foundBooking = mongoTemplate.findOne(query, Booking.class);

        if (foundBooking != null) {
            log.info("Booking {} found", id);
            return Optional.of(foundBooking);
        } else {
            log.warn("Booking with id {} not found", id);
            return Optional.empty();
        }
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        log.debug("Returning a list of all bookings");

        // Retrieve all bookings using the repository
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::mapToBookingResponse)
                .toList();
    }

    private BookingResponse mapToBookingResponse(Booking bookingEntity) {
        // Mapping method to convert booking to BookingResponse
        return new BookingResponse(
                bookingEntity.getId(),
                bookingEntity.getRoomId(),
                bookingEntity.getUserId(),
                bookingEntity.getStartTime(),
                bookingEntity.getEndTime()
        );
    }

    @Override
    public String updateBooking(String id, BookingRequest bookingRequest) {
        log.debug("Updating booking with id {}", id);

        // Use MongoTemplate to find the booking by ID
        Query query = new Query(Criteria.where("id").is(id));
        Booking foundBooking = mongoTemplate.findOne(query, Booking.class);

        if (foundBooking != null) {
            // Update the booking details
            foundBooking.setRoomId(bookingRequest.roomId());
            foundBooking.setUserId(bookingRequest.userId());
            foundBooking.setStartTime(bookingRequest.startTime());
            foundBooking.setEndTime(bookingRequest.endTime());

            // Save the updated booking
            bookingRepository.save(foundBooking);
            log.info("Booking {} updated", id);
            return foundBooking.getId();
        }

        log.warn("Booking with id {} not found", id);
        return null;
    }

    @Override
    public void deleteBooking(String id) {
        log.debug("Deleting booking with id {}", id);

        // Use MongoTemplate to find the booking by ID
        Query query = new Query(Criteria.where("id").is(id));
        Booking foundBooking = mongoTemplate.findOne(query, Booking.class);

        if (foundBooking != null) {
            // Delete the booking from the repository
            bookingRepository.delete(foundBooking);
            log.info("Booking {} deleted", id);
        } else {
            log.warn("Booking with id {} not found", id);
        }
    }
}
