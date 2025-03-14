package ca.gbc.approvalservice.dto;

public record ApprovalRequest(
        String eventId,
        String approvedId,
        boolean approved
) {}