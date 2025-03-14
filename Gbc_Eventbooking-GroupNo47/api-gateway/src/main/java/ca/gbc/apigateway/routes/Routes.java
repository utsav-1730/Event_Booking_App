package ca.gbc.apigateway.routes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
@Slf4j
public class Routes {
    @Value("${services.room.url}")
    public String roomServiceUrl;
    @Value("${services.booking.url}")
    public String bookingServiceUrl;
    @Value("${services.user.url}")
    public String userServiceUrl;
    @Value("${services.approval.url}")
    public String approvalServiceUrl;
    @Value("${services.event.url}")
    public String eventServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> roomServiceRoute(){
        //printf("%s", .. variable);
        log.info("Initialising room service route with URL: {}",roomServiceUrl);

        return GatewayRouterFunctions.route("room_service")
                .route(RequestPredicates.path("/api/room"), request -> {
                    log.info("Received request for room-service: {}",request.uri());
                    return HandlerFunctions.http(roomServiceUrl).handle(request);
                })
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("roomServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoute(){
        //printf("%s", .. variable);
        log.info("Initialising booking service route with URL: {}",bookingServiceUrl);

        return GatewayRouterFunctions.route("booking_service")
                .route(RequestPredicates.path("/api/bookings"), request -> {
                    log.info("Received request for booking-service: {}",request.uri());
                    return HandlerFunctions.http(bookingServiceUrl).handle(request);
                })
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("bookingServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> userServiceRoute(){
        //printf("%s", .. variable);
        log.info("Initialising user service route with URL: {}",userServiceUrl);

        return GatewayRouterFunctions.route("user_service")
                .route(RequestPredicates.path("/api/users"), request -> {
                    log.info("Received request for user-service: {}",request.uri());
                    return HandlerFunctions.http(userServiceUrl).handle(request);
                })
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("userServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> approvalServiceRoute(){
        //printf("%s", .. variable);
        log.info("Initialising approval service route with URL: {}",approvalServiceUrl);

        return GatewayRouterFunctions.route("approval_service")
                .route(RequestPredicates.path("/api/approvals"), request -> {
                    log.info("Received request for approval-service: {}",request.uri());
                    return HandlerFunctions.http(approvalServiceUrl).handle(request);
                })
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("approvalServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> eventServiceRoute(){
        //printf("%s", .. variable);
        log.info("Initialising event service route with URL: {}",eventServiceUrl);

        return GatewayRouterFunctions.route("event_service")
                .route(RequestPredicates.path("/api/events"), request -> {
                    log.info("Received request for event-service: {}",request.uri());
                    return HandlerFunctions.http(eventServiceUrl).handle(request);
                })
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("eventServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> roomServiceSwagger(){
        return GatewayRouterFunctions.route("room_service_swagger")
                .route(RequestPredicates.path("/aggregate/room_service/api-docs"),
                        HandlerFunctions.http(roomServiceUrl + "/api-docs"))
                .filter(setPath("/api-docs"))// Add `/api-docs` to the URL
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("roomSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceSwagger(){
        return GatewayRouterFunctions.route("booking_service_swagger")
                .route(RequestPredicates.path("/aggregate/booking_service/api-docs"),
                        HandlerFunctions.http(bookingServiceUrl + "/api-docs"))
                .filter(setPath("/api-docs"))// Add `/api-docs` to the URL
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("bookingSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceSwagger(){
        return GatewayRouterFunctions.route("user_service_swagger")
                .route(RequestPredicates.path("/aggregate/user_service/api-docs"),
                        HandlerFunctions.http(userServiceUrl))
                .filter(setPath("/api-docs"))// Add `/api-docs` to the URL
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("userSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> approvalServiceSwagger(){
        return GatewayRouterFunctions.route("approval_service_swagger")
                .route(RequestPredicates.path("/aggregate/approval_service/api-docs"),
                        HandlerFunctions.http(approvalServiceUrl + "/api-docs"))
                .filter(setPath("/api-docs"))// Add `/api-docs` to the URL
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("approvalSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> eventServiceSwagger(){
        return GatewayRouterFunctions.route("event_service_swagger")
                .route(RequestPredicates.path("/aggregate/event_service/api-docs"),
                        HandlerFunctions.http(eventServiceUrl + "/api-docs"))
                .filter(setPath("/api-docs"))// Add `/api-docs` to the URL
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("eventSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route()
                .GET("/fallbackRoute", request ->
                        ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Service is temporarily unavailable, please try again later"))
                .build();
    }
}
