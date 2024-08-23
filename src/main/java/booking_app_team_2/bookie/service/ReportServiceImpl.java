package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Report;
import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.repository.ReportRepository;
import booking_app_team_2.bookie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;
    private UserRepository userRepository;
    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public Page<Report> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Report> findOne(Long id) {
        return reportRepository.findById(id);
    }

    @Override
    public Report save(Report report) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Boolean blockUser(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (report.isPresent()) {
            User user = report.get().getReportee();
            user.setBlocked(true);
            userRepository.save(user);
            reportRepository.delete(report.get());
            return true;
        }
        return false;
    }
}
