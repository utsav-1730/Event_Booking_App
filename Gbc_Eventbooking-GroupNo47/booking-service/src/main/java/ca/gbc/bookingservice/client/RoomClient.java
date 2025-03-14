package ca.gbc.bookingservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "room-client", url = "${room.service.url}/api/room")
public interface RoomClient {
    Logger log= LoggerFactory.getLogger(RoomClient.class);
    @RequestMapping(method = RequestMethod.GET, value="/checkAvailability")
    @CircuitBreaker(name = "room", fallbackMethod = "fallbackMethod")
    @Retry(name = "room")
    boolean isAvailable(@RequestParam("roomId") String roomId);

    @RequestMapping(method = RequestMethod.POST, value="/updateAvailability")
    @CircuitBreaker(name = "room", fallbackMethod = "fallback")
    @Retry(name = "room")
    void updateAvailability(@RequestParam("roomId") String roomId, @RequestParam("availability") Boolean availability);

    default boolean fallbackMethod(String roomid, Throwable throwable) {
        log.info("Cannot get room availablity for room id '{}', failure reason: {}", roomid, throwable.getMessage());
        return false; // Fallback response when the inventory service is unavailable
    }
    default void fallback(String roomid, Throwable throwable) {
        log.info("Cannot update availblity for room id '{}', failure reason: {}", roomid, throwable.getMessage());
    }
}