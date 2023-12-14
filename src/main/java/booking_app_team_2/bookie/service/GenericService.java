package booking_app_team_2.bookie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GenericService<T> {
    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    Optional<T> findOne(Long id);

    T save(T t);

    void remove(Long id);

    // TODO: Declare shared service methods
}
