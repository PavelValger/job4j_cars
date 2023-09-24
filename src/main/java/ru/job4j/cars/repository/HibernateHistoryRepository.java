package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.History;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateHistoryRepository implements HistoryRepository {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateCarRepository.class.getName());
    private final CrudRepository crudRepository;

    @Override
    public Optional<History> save(History history) {
        try {
            crudRepository.run(session -> session.persist(history));
            return Optional.of(history);
        } catch (Exception e) {
            LOG.info("Неудачная попытка сохранения времени владения, Exception in log example", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.numberRowsRequest(
                "delete History where id = :fId",
                Map.of("fId", id)
        ) > 0;
    }

    @Override
    public void update(History history) {
        crudRepository.run(session -> session.merge(history));
    }

    @Override
    public Optional<History> findById(int id) {
        return crudRepository.optional(
                "FROM History WHERE id = :fId", History.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<History> findAll() {
        return crudRepository.query(
                "from History order by id", History.class);
    }
}
