package ca.gbc.approvalservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-client", url = "${user.service.url}/api/users")
public interface UserClient {
    Logger log = LoggerFactory.getLogger(UserClient.class);
    @RequestMapping(method = RequestMethod.GET, value="/isValid")
    @CircuitBreaker(name = "user", fallbackMethod = "fallbackMethod")
    @Retry(name = "user")
    boolean isValid(@RequestParam("userId") String userId);

    @RequestMapping(method = RequestMethod.GET, value="/isUserStaff")
    @CircuitBreaker(name = "user", fallbackMethod = "fallback")
    @Retry(name = "user")
    boolean isUserStaff(@RequestParam("userId") String userId);

    default boolean fallbackMethod(String userid, Throwable throwable) {
        log.info("Cannot get validity for user id '{}', failure reason: {}", userid, throwable.getMessage());
        return false; // Fallback response when the inventory service is unavailable
    }
    default boolean fallback(String userid, Throwable throwable) {
        log.info("Cannot confirm user role for user id '{}', failure reason: {}", userid, throwable.getMessage());
        return false; // Fallback response when the inventory service is unavailable
    }
}

