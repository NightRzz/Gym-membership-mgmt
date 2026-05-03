package sii.GymMembership.plan.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sii.GymMembership.plan.PlanType;

import java.math.BigDecimal;

public record CreateMembershipPlanRequest(
	@NotNull(message = "Plan type is required")
	PlanType type,

	@NotBlank(message = "Plan name is required")
	String name,

	@NotNull(message = "Price is required")
	@DecimalMin(value = "0.01", message = "Price must be greater than 0")
	BigDecimal price
) {
}