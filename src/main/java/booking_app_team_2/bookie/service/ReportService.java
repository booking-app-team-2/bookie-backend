package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Report;

public interface ReportService extends GenericService<Report>{
    Boolean blockUser(Long id);
}
