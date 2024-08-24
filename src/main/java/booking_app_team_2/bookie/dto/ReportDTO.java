package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Report;
import booking_app_team_2.bookie.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReportDTO {
    private Long id = null;
    private String body;
    private String reporterName;
    private String reporteeName;

    @JsonIgnore
    public ReportDTO(Report report) {
        this.id = report.getId();
        this.body = report.getBody();
        this.reporterName = report.getReporter().getName();
        this.reporteeName = report.getReportee().getName();
    }
}
