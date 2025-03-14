package ca.gbc.approvalservice.dto;

import java.time.LocalDateTime;

public record ApprovalResponse(
        String id,
        String eventId,
        String approvedId,
        boolean approved,
        LocalDateTime approvalDate
) {}