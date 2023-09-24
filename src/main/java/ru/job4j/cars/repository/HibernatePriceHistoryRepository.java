package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePriceHistoryRepository implements PriceHistoryRepository {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateCarRepository.class.getName());
    private final CrudRepository crudRepository;

    @Override
    public Optional<PriceHistory> save(PriceHistory priceHistory) {
        try {
            crudRepository.run(session -> session.persist(priceHistory));
            return Optional.of(priceHistory);
        } catch (Exception e) {
            LOG.info("Неудачная попытка сохранения истории владения автомобилем, Exception in log example", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.numberRowsRequest(
                "delete PriceHistory where id = :fId",
                Map.of("fId", id)
        ) > 0;
    }

    @Override
    public void update(PriceHistory priceHistory) {
        crudRepository.run(session -> session.merge(priceHistory));
    }

    @Override
    public Optional<PriceHistory> findById(int id) {
        return crudRepository.optional(
                "FROM PriceHistory WHERE id = :fId", PriceHistory.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<PriceHistory> findAll() {
        return crudRepository.query(
                "from PriceHistory order by id", PriceHistory.class);
    }
}
