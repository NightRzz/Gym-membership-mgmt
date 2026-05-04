package sii.GymMembership.report;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sii.GymMembership.report.dto.RevenueRow;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RevenueReportService {

    private final RevenueReportRepository revenueReportRepository;

    public RevenueReportService(RevenueReportRepository revenueReportRepository) {
        this.revenueReportRepository = revenueReportRepository;
    }

    public List<RevenueRow> getRevenuePerGymByCurrency() {
        return revenueReportRepository.revenuePerGymByCurrency();
    }
}

