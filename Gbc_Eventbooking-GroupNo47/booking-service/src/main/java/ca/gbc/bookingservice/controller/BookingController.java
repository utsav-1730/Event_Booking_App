package ca.gbc.bookingservice.controller;

import ca.gbc.bookingservice.client.RoomClient;
import ca.gbc.bookingservice.client.UserClient;
import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.model.Booking;
import ca.gbc.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final RoomClient roomClient;
    private final UserClient userClient;

    // Create a new booking
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) {

        if(!userClient.isValid(bookingRequest.userId())) {
            throw new RuntimeException("User id is invalid");
        }

        if(!roomClient.isAvailable(bookingRequest.roomId())) {
            throw new RuntimeException("Room no " + bookingRequest.roomId() + " is not available");
        }

        roomClient.updateAvailability(bookingRequest.roomId(), false);

        BookingResponse createdBooking = bookingService.createBooking(bookingRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/bookings/" + createdBooking.id());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdBooking);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable("id") String id) {
        Optional<Booking> booking = bookingService.getBookingById(id);

        if (booking.isPresent()) {
            BookingResponse bookingResponse = new BookingResponse(
                    booking.get().getId(),
                    booking.get().getRoomId(),
                    booking.get().getUserId(),
                    booking.get().getStartTime(),
                    booking.get().getEndTime()
            );
            return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable("id") String id,
                                           @RequestBody BookingRequest bookingRequest) {
        String updatedBookingId = bookingService.updateBooking(id, bookingRequest);

        if (updatedBookingId != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/api/bookings/" + updatedBookingId);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") String id) {
        bookingService.deleteBooking(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
