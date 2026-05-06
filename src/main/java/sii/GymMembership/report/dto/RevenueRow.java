package sii.GymMembership.report.dto;

import java.math.BigDecimal;

public record RevenueRow(
    String gymName,
    BigDecimal amount,
    String currency
) {
}

