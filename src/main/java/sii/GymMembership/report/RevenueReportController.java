package sii.GymMembership.report;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sii.GymMembership.report.dto.RevenueRow;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class RevenueReportController {

    private final RevenueReportService revenueReportService;

    public RevenueReportController(RevenueReportService revenueReportService) {
        this.revenueReportService = revenueReportService;
    }

    @GetMapping("/revenue")
    public ResponseEntity<List<RevenueRow>> getRevenueReport() {
        List<RevenueRow> rows = revenueReportService.getRevenuePerGymByCurrency();
        return ResponseEntity.ok(rows);
    }
}

