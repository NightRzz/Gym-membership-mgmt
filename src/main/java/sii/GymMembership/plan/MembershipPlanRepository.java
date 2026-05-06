package sii.GymMembership.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {

	Optional<MembershipPlan> findByGymIdAndNameIgnoreCase(Long gymId, String name);

	Optional<MembershipPlan> findByIdAndGym_Id(Long id, Long gymId);

	List<MembershipPlan> findAllByGymIdOrderByNameAsc(Long gymId);
}
