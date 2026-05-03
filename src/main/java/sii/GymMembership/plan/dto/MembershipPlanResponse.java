package sii.GymMembership.plan.dto;

import sii.GymMembership.plan.PlanType;

import java.math.BigDecimal;

public record MembershipPlanResponse(
	Long id,
	Long gymId,
	PlanType type,
	String name,
	BigDecimal price
) {
}
