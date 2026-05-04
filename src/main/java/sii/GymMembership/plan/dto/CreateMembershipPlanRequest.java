package sii.GymMembership.plan.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
	MoneyRequest monthlyPrice,

	@NotNull(message = "Duration in months is required")
	@Min(value = 1, message = "Duration must be at least 1 month")
	Integer durationMonths,

	@NotNull(message = "Max members is required")
	@Min(value = 1, message = "Max members must be at least 1")
	Integer maxMembers
) {
}