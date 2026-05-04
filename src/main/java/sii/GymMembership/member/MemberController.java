package sii.GymMembership.member;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sii.GymMembership.member.dto.CreateMemberRequest;
import sii.GymMembership.member.dto.MemberResponse;

import java.util.List;

@RestController
@RequestMapping("/api/gyms/{gymId}/members")
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping
	public ResponseEntity<MemberResponse> createMember(
			@PathVariable Long gymId,
			@Valid @RequestBody CreateMemberRequest request) {
		MemberResponse response = memberService.createMember(gymId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/{memberId}/cancel")
	public ResponseEntity<MemberResponse> cancelMembership(
			@PathVariable Long gymId,
			@PathVariable Long memberId) {
		return ResponseEntity.ok(memberService.cancelMembership(gymId, memberId));
	}

	@GetMapping
	public ResponseEntity<List<MemberResponse>> getMembersForGym(@PathVariable Long gymId) {
		return ResponseEntity.ok(memberService.getMembersForGym(gymId));
	}
}
