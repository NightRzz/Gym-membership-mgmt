package sii.GymMembership.member.dto;

import sii.GymMembership.member.MemberStatus;

import java.time.LocalDate;

public record MemberResponse(
	Long id,
	Long gymId,
	String gymName,
	Long membershipPlanId,
	String membershipPlanName,
	String fullName,
	String email,
	LocalDate membershipStartDate,
	MemberStatus status
) {
}
