package ca.gbc.approvalservice;

import org.springframework.boot.SpringApplication;

public class TestApprovalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ApprovalServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
