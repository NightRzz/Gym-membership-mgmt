package sii.GymMembership.plan.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sii.GymMembership.plan.PlanType;

public record CreateMembershipPlanRequest(
	@NotNull(message = "Plan type is required")
	PlanType type,

	@NotBlank(message = "Plan name is required")
	String name,

	@NotNull(message = "Monthly price is required")
	@Valid
	MoneyRequest monthlyPrice
) {
}