package ca.gbc.eventservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "room-client", url = "${room.service.url}/api/room")
public interface RoomClient {
    @RequestMapping(method = RequestMethod.GET, value="/checkAvailability")
    boolean isAvailable(@RequestParam("roomId") String roomId);
}