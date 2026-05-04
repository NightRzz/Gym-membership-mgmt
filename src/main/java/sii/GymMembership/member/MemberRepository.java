package sii.GymMembership.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findByMembershipPlan_Gym_IdOrderByFullNameAsc(Long gymId);

	long countByMembershipPlan_IdAndStatus(Long membershipPlanId, MemberStatus status);

	boolean existsByEmailIgnoreCaseAndMembershipPlan_Gym_IdAndStatus(
			String email,
			Long gymId,
			MemberStatus status);
}
