package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ReservationSpecification {
    public static Specification<Reservation> hasAccommodationNameLike(String accommodationNameSubstring) {
        return (root, query, criteriaBuilder) -> {
            Join<Reservation, Accommodation> accommodation = root.join("accommodation");

            return criteriaBuilder.like(accommodation.get("name"), "%" + accommodationNameSubstring + "%");
        };
    }

    public static Specification<Reservation> hasPeriodBetween(Period searchPeriod) {
        return (root, query, criteriaBuilder) -> {
            if (searchPeriod == null)
                return criteriaBuilder.and();

            Join<Reservation, Period> period = root.join("period");

            return criteriaBuilder
                    .and(
                            criteriaBuilder
                                    .between(
                                            period.get("startDate"),
                                            searchPeriod.getStartDate(),
                                            searchPeriod.getEndDate()
                                    ),
                            criteriaBuilder
                                    .between(
                                            period.get("endDate"),
                                            searchPeriod.getStartDate(),
                                            searchPeriod.getEndDate()
                                    )
                    );
        };
    }

    public static Specification<Reservation> hasStatusIn(List<ReservationStatus> statuses) {
        return (root, query, criteriaBuilder) -> root.get("status").in(statuses);
    }

    public static Specification<Reservation> hasReserveeEqualTo(Guest reservee) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("reservee"), reservee);
    }

    public static Specification<Reservation> hasAccommodationOwnerEqualTo(Owner owner) {
        return (root, query, criteriaBuilder) -> root.get("accommodation").in(owner.getAccommodations());
    }
}