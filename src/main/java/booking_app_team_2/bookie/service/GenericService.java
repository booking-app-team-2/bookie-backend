package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.repository.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class GenericService<T> {
    protected final GenericRepository<T> repository;

    public GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<T> findOne(Long id) {
        return repository.findById(id);
    }

    public T save(T t) {
        return repository.save(t);
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }
}
