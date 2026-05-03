package sii.GymMembership.plan;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sii.GymMembership.common.exception.DuplicateMembershipPlanNameException;
import sii.GymMembership.common.exception.GymNotFoundException;
import sii.GymMembership.gym.GymRepository;
import sii.GymMembership.plan.dto.CreateMembershipPlanRequest;
import sii.GymMembership.plan.dto.MembershipPlanResponse;

import java.util.List;

@Service
@Transactional
public class MembershipPlanService {

	private final GymRepository gymRepository;
	private final MembershipPlanRepository membershipPlanRepository;

	public MembershipPlanService(
			GymRepository gymRepository,
			MembershipPlanRepository membershipPlanRepository) {
		this.gymRepository = gymRepository;
		this.membershipPlanRepository = membershipPlanRepository;
	}

	public MembershipPlanResponse createPlan(Long gymId, CreateMembershipPlanRequest request) {
		var gym = gymRepository.findById(gymId)
			.orElseThrow(() -> new GymNotFoundException("Gym with id " + gymId + " not found"));

		if (membershipPlanRepository.findByGymIdAndName(gymId, request.name()).isPresent()) {
			throw new DuplicateMembershipPlanNameException(
				"Plan with name '" + request.name() + "' already exists for this gym");
		}

		MembershipPlan plan = new MembershipPlan();
		plan.setGym(gym);
		plan.setType(request.type());
		plan.setName(request.name());
		plan.setPrice(request.price());

		MembershipPlan saved = membershipPlanRepository.save(plan);
		return toResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<MembershipPlanResponse> getPlansForGym(Long gymId) {
		if (!gymRepository.existsById(gymId)) {
			throw new GymNotFoundException("Gym with id " + gymId + " not found");
		}
		return membershipPlanRepository.findAllByGymIdOrderByNameAsc(gymId).stream()
			.map(this::toResponse)
			.toList();
	}

	private MembershipPlanResponse toResponse(MembershipPlan plan) {
		return new MembershipPlanResponse(
			plan.getId(),
			plan.getGym().getId(),
			plan.getType(),
			plan.getName(),
			plan.getPrice()
		);
	}
}
