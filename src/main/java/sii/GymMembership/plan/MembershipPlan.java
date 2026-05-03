package sii.GymMembership.plan;

import jakarta.persistence.*;
import lombok.*;
import sii.GymMembership.gym.Gym;

@Entity
@Table(
	name = "membership_plans",
	uniqueConstraints = @UniqueConstraint(columnNames = {"gym_id", "name"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "gym_id", nullable = false)
	private Gym gym;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PlanType type;

	@Column(nullable = false)
	private String name;

	@Embedded
	private Money monthlyPrice;
}
