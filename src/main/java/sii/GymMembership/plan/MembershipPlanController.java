package sii.GymMembership.plan;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sii.GymMembership.plan.dto.CreateMembershipPlanRequest;
import sii.GymMembership.plan.dto.MembershipPlanResponse;

import java.util.List;

@RestController
@RequestMapping("/api/gyms/{gymId}/plans")
public class MembershipPlanController {

	private final MembershipPlanService membershipPlanService;

	public MembershipPlanController(MembershipPlanService membershipPlanService) {
		this.membershipPlanService = membershipPlanService;
	}

	@PostMapping
	public ResponseEntity<MembershipPlanResponse> createPlan(
			@PathVariable Long gymId,
			@Valid @RequestBody CreateMembershipPlanRequest request) {
		MembershipPlanResponse response = membershipPlanService.createPlan(gymId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<MembershipPlanResponse>> getPlansForGym(@PathVariable Long gymId) {
		return ResponseEntity.ok(membershipPlanService.getPlansForGym(gymId));
	}
}
