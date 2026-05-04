package sii.GymMembership.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sii.GymMembership.member.Member;
import sii.GymMembership.report.dto.RevenueRow;

import java.util.List;

@Repository
public interface RevenueReportRepository extends JpaRepository<Member, Long> {

    @Query("""
        select new sii.GymMembership.report.dto.RevenueRow(
            g.name, sum(p.monthlyPrice.amount), p.monthlyPrice.currencyCode)
        from Member m
        join m.membershipPlan p
        join p.gym g
        where m.status = sii.GymMembership.member.MemberStatus.ACTIVE
        group by g.name, p.monthlyPrice.currencyCode
        order by g.name, p.monthlyPrice.currencyCode
    """)
    List<RevenueRow> revenuePerGymByCurrency();
}

