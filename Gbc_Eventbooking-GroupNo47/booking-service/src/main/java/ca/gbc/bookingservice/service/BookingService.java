package ca.gbc.bookingservice.service;
import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);
    Optional<Booking> getBookingById(String id);
    List<BookingResponse> getAllBookings();
    String updateBooking(String id, BookingRequest bookingRequest);
    void deleteBooking(String id);
}