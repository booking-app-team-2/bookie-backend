package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.AccommodationReview;
import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.domain.ReservationStatus;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.repository.AccommodationReviewRepository;
import booking_app_team_2.bookie.repository.ReservationRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Getter
@Service
public class AccommodationReviewServiceImpl implements AccommodationReviewService {

    private final AccommodationReviewRepository accommodationReviewRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public AccommodationReviewServiceImpl(AccommodationReviewRepository accommodationReviewRepository, ReservationRepository reservationRepository) {
        this.accommodationReviewRepository = accommodationReviewRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<AccommodationReview> findAll() {
        return null;
    }

    @Override
    public Page<AccommodationReview> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<AccommodationReview> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public AccommodationReview save(AccommodationReview accommodationReview) {
        return accommodationReviewRepository.save(accommodationReview);
    }

    @Override
    public void remove(Long id) {
        if (accommodationReviewRepository.findById(id).isEmpty())
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No such review exists.");
        accommodationReviewRepository.deleteById(id);
    }

    @Override
    public List<AccommodationReview> findAccommodationReviews(Long accommodationId) {
        return accommodationReviewRepository.findAllByAccommodation_IdAndIsApproved(accommodationId, true);
    }

    @Override
    public void addAccommodationReview(AccommodationReview accommodationReview) {
        if (accommodationReview.getReviewer() == null) {
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No such user exists.");
        }
        if (accommodationReview.getAccommodation() == null) {
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No such accommodation exists.");
        }
        List<Reservation> reservations = reservationRepository.findAllByReserveeAndStatusAndAccommodation_Id(accommodationReview.getReviewer(), ReservationStatus.Accepted, accommodationReview.getAccommodation().getId());
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                if (reservation.getPeriod().getEndDate().isBefore(LocalDate.now()) && ChronoUnit.DAYS.between(reservation.getPeriod().getEndDate(), LocalDate.now()) <= 7) {
                    this.save(accommodationReview);
                    return;
                }
            }
        }
        throw new HttpTransferException(HttpStatus.FORBIDDEN, "This user does not meet the criteria to comment");
    }

    @Override
    public Float calculateAverageGrade(Collection<AccommodationReview> accommodationReviews) {
        float gradeSum = 0;
        for (AccommodationReview accommodationReview : accommodationReviews) {
            gradeSum += accommodationReview.getGrade();
        }
        return gradeSum / accommodationReviews.size();
    }

    @Override
    public void approveReview(Long reviewId) {
        Optional<AccommodationReview> optionalAccommodationReview = accommodationReviewRepository.findById(reviewId);
        if (optionalAccommodationReview.isEmpty()) {
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No such review exists.");
        }
        AccommodationReview accommodationReview = optionalAccommodationReview.get();
        accommodationReview.setApproved(true);
        accommodationReviewRepository.save(accommodationReview);
    }
    @Override
    public List<AccommodationReview> findUnapprovedReviews() {
        return accommodationReviewRepository.findAllByIsApproved(false);
    }
}
