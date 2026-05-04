package sii.GymMembership.plan.dto;

import sii.GymMembership.plan.PlanType;

public record MembershipPlanResponse(
	Long id,
	Long gymId,
	PlanType type,
	String name,
	MoneyResponse monthlyPrice,
	int durationMonths,
	int maxMembers
) {
}
