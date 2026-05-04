package sii.GymMembership.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sii.GymMembership.common.exception.DuplicateMemberEmailException;
import sii.GymMembership.common.exception.GymNotFoundException;
import sii.GymMembership.common.exception.MemberNotFoundException;
import sii.GymMembership.common.exception.MembershipPlanNotFoundException;
import sii.GymMembership.common.exception.PlanAtCapacityException;
import sii.GymMembership.gym.GymRepository;
import sii.GymMembership.member.dto.CreateMemberRequest;
import sii.GymMembership.member.dto.MemberResponse;
import sii.GymMembership.plan.MembershipPlan;
import sii.GymMembership.plan.MembershipPlanRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MemberService {

	private final Clock clock;
	private final GymRepository gymRepository;
	private final MembershipPlanRepository membershipPlanRepository;
	private final MemberRepository memberRepository;

	public MemberService(
			Clock clock,
			GymRepository gymRepository,
			MembershipPlanRepository membershipPlanRepository,
			MemberRepository memberRepository) {
		this.clock = clock;
		this.gymRepository = gymRepository;
		this.membershipPlanRepository = membershipPlanRepository;
		this.memberRepository = memberRepository;
	}

	public MemberResponse createMember(Long gymId, CreateMemberRequest request) {
		if (!gymRepository.existsById(gymId)) {
			throw new GymNotFoundException("Gym with id " + gymId + " not found");
		}

		String email = request.email().trim();
		if (memberRepository.existsByEmailIgnoreCaseAndMembershipPlan_Gym_IdAndStatus(
				email, gymId, MemberStatus.ACTIVE)) {
			throw new DuplicateMemberEmailException(
				"An active member with email '" + email + "' already exists for this gym");
		}

		MembershipPlan plan = membershipPlanRepository
			.findByIdAndGym_Id(request.planId(), gymId)
			.orElseThrow(() -> new MembershipPlanNotFoundException(
				"Membership plan with id " + request.planId() + " not found for gym " + gymId));

		long activeCount = memberRepository.countByMembershipPlan_IdAndStatus(plan.getId(), MemberStatus.ACTIVE);
		if (activeCount >= plan.getMaxMembers()) {
			throw new PlanAtCapacityException(
				"Membership plan '" + plan.getName() + "' has reached its member limit (" + plan.getMaxMembers() + ")");
		}

		Member member = new Member();
		member.setFullName(request.fullName().trim());
		member.setEmail(email);
		member.setMembershipStartDate(LocalDate.now(clock));
		member.setMembershipPlan(plan);
		member.setStatus(MemberStatus.ACTIVE);

		Member saved = memberRepository.save(member);
		return toResponse(saved);
	}

	public MemberResponse cancelMembership(Long gymId, Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException("Member with id " + memberId + " not found"));

		if (!member.getMembershipPlan().getGym().getId().equals(gymId)) {
			throw new MemberNotFoundException("Member with id " + memberId + " not found for gym " + gymId);
		}

		if (member.getStatus() != MemberStatus.CANCELLED) {
			member.setStatus(MemberStatus.CANCELLED);
			memberRepository.save(member);
		}

		return toResponse(member);
	}

	@Transactional(readOnly = true)
	public List<MemberResponse> getMembersForGym(Long gymId) {
		if (!gymRepository.existsById(gymId)) {
			throw new GymNotFoundException("Gym with id " + gymId + " not found");
		}
		return memberRepository.findByMembershipPlan_Gym_IdOrderByFullNameAsc(gymId).stream()
			.map(this::toResponse)
			.toList();
	}

	private MemberResponse toResponse(Member member) {
		MembershipPlan plan = member.getMembershipPlan();
		return new MemberResponse(
			member.getId(),
			plan.getGym().getId(),
			plan.getId(),
			plan.getName(),
			member.getFullName(),
			member.getEmail(),
			member.getMembershipStartDate(),
			member.getStatus()
		);
	}
}
