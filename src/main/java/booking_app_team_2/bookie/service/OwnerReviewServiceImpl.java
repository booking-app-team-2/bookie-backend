package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.OwnerReview;
import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.domain.ReservationStatus;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.repository.OwnerReviewRepository;
import booking_app_team_2.bookie.repository.ReservationRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Getter
@Service
public class OwnerReviewServiceImpl implements OwnerReviewService {

    private OwnerReviewRepository ownerReviewRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public OwnerReviewServiceImpl(OwnerReviewRepository ownerReviewRepository, ReservationRepository reservationRepository) {
        this.ownerReviewRepository = ownerReviewRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<OwnerReview> findAll() {
        return null;
    }

    @Override
    public Page<OwnerReview> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<OwnerReview> findOne(Long id) {
        return ownerReviewRepository.findById(id);
    }

    @Override
    public OwnerReview save(OwnerReview ownerReview) {
        return ownerReviewRepository.save(ownerReview);
    }

    @Override
    public void remove(Long id) {
        if (ownerReviewRepository.findById(id).isEmpty())
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No such review exists.");
        ownerReviewRepository.deleteById(id);
    }

    @Override
    public List<OwnerReview> findOwnerReviews(Long ownerId) {
        return ownerReviewRepository.findAllByReviewee_IdAndIsApproved(ownerId, true);
    }

    @Override
    public Float calculateAverageGrade(Collection<OwnerReview> ownerReviews) {
        float gradeSum = 0;
        for (OwnerReview ownerReview : ownerReviews) {
            gradeSum += ownerReview.getGrade();
        }
        return gradeSum / ownerReviews.size();
    }

    @Override
    public void addOwnerReview(OwnerReview ownerReview) {
        if (ownerReview.getReviewer() == null) {
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No such reviewer exists.");
        }
        if (ownerReview.getReviewee() == null) {
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No such reviewee exists.");
        }
        for (Accommodation accommodation : ownerReview.getReviewee().getAccommodations()) {
            List<Reservation> reservations = reservationRepository.findAllByReserveeAndStatusAndAccommodation_Id(ownerReview.getReviewer(), ReservationStatus.Accepted, accommodation.getId());
            if (reservations.isEmpty()) continue;
            for (Reservation reservation : reservations) {
                if (reservation.getPeriod().getEndDate().isBefore(LocalDate.now())) {
                    this.save(ownerReview);
                    return;
                }
            }
        }
        throw new HttpTransferException(HttpStatus.FORBIDDEN, "This user does not meet the criteria to comment");
    }

    @Override
    public List<OwnerReview> findUnapprovedReviews() {
        return ownerReviewRepository.findAllByIsApproved(false);
    }

    @Override
    public List<OwnerReview> findReportedReviews() {
        return ownerReviewRepository.findAllByIsReported(true);
    }

    @Override
    public void approveReview(Long reviewId) {
        Optional<OwnerReview> optionalOwnerReview = ownerReviewRepository.findById(reviewId);
        if (optionalOwnerReview.isEmpty()) {
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No such review exists.");
        }
        OwnerReview ownerReview = optionalOwnerReview.get();
        ownerReview.setApproved(true);
        this.save(ownerReview);
    }

    @Override
    public void reportReview(Long reviewId) {
        Optional<OwnerReview> optionalOwnerReview = ownerReviewRepository.findById(reviewId);
        if (optionalOwnerReview.isEmpty()) {
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No such review exists.");
        }
        OwnerReview ownerReview = optionalOwnerReview.get();
        ownerReview.setReported(true);
        this.save(ownerReview);
    }

}