package sii.GymMembership.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMemberRequest(
	@NotNull(message = "Membership plan id is required")
	Long planId,

	@NotBlank(message = "Full name is required")
	String fullName,

	@NotBlank(message = "Email is required")
	@Email(message = "Email must be a valid address")
	String email
) {
}
