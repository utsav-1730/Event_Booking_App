package ca.gbc.approvalservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "event-client", url = "${event.service.url}/api/events")
public interface EventClient {

    @RequestMapping(method = RequestMethod.GET, value="/isEventValid")
    boolean isEventValid(@RequestParam("eventId") String eventId);

    @RequestMapping(method = RequestMethod.POST, value="/updateEventStatus")
    void updateEventStatus(@RequestParam("eventId") String eventId, @RequestParam("status") String status);
}
