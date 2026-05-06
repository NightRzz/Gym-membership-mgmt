package sii.GymMembership.member;

import jakarta.persistence.*;
import lombok.*;
import sii.GymMembership.plan.MembershipPlan;

import java.time.LocalDate;

@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "full_name", nullable = false)
	private String fullName;

	@Column(nullable = false)
	private String email;

	@Column(name = "membership_start_date", nullable = false)
	private LocalDate membershipStartDate;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "membership_plan_id", nullable = false)
	private MembershipPlan membershipPlan;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberStatus status = MemberStatus.ACTIVE;
}
