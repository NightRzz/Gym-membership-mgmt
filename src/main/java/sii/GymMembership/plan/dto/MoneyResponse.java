package sii.GymMembership.plan.dto;

import java.math.BigDecimal;

public record MoneyResponse(
	BigDecimal amount,
	String currency
) {
}
