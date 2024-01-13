package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Period;
import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.domain.ReservationStatus;
import jakarta.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ReservationSpecification {
    private static String likePattern(String value) {
        return "%" + value + "%";
    }

    public static Specification<Reservation> hasAccommodationNameLike(String accommodationNameSubstring) {
        return (root, query, criteriaBuilder) -> {
            Join<Reservation, Accommodation> accommodation = root.join("accommodation");

            return criteriaBuilder
                    .like(
                            accommodation.get("name"),
                            StringUtils.isBlank(accommodationNameSubstring)
                                    ? likePattern("") : likePattern(accommodationNameSubstring)
                    );
        };
    }

    // TODO: Implement

    public static Specification<Reservation> hasPeriodBetween(Period period) {
        return (root, query, criteriaBuilder) -> {
            return null;
        };
    }

    public static Specification<Reservation> hasStatusIn(List<ReservationStatus> statuses) {
        return (root, query, criteriaBuilder) -> root.get("status").in(statuses);
    }
}