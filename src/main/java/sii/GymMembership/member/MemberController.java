package sii.GymMembership.member;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sii.GymMembership.member.dto.CreateMemberRequest;
import sii.GymMembership.member.dto.MemberResponse;

import java.util.List;

@RestController
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/api/members")
	public ResponseEntity<List<MemberResponse>> getAllMembers() {
		return ResponseEntity.ok(memberService.getAllMembers());
	}

	@PostMapping("/api/gyms/{gymId}/members")
	public ResponseEntity<MemberResponse> createMember(
			@PathVariable Long gymId,
			@Valid @RequestBody CreateMemberRequest request) {
		MemberResponse response = memberService.createMember(gymId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/api/gyms/{gymId}/members/{memberId}/cancel")
	public ResponseEntity<MemberResponse> cancelMembership(
			@PathVariable Long gymId,
			@PathVariable Long memberId) {
		return ResponseEntity.ok(memberService.cancelMembership(gymId, memberId));
	}

	@GetMapping("/api/gyms/{gymId}/members")
	public ResponseEntity<List<MemberResponse>> getMembersForGym(@PathVariable Long gymId) {
		return ResponseEntity.ok(memberService.getMembersForGym(gymId));
	}
}
