package ca.gbc.roomservice;

import org.springframework.boot.SpringApplication;

public class TestRoomServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(RoomServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
