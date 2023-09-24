package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateCarRepository implements CarRepository {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateCarRepository.class.getName());
    private final CrudRepository crudRepository;

    @Override
    public Optional<Car> save(Car car) {
        try {
            crudRepository.run(session -> session.persist(car));
            return Optional.of(car);
        } catch (Exception e) {
            LOG.info("Неудачная попытка сохранения автомобиля, Exception in log example", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.numberRowsRequest(
                "delete Car where id = :fId",
                Map.of("fId", id)
        ) > 0;
    }

    @Override
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    @Override
    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                "FROM Car c JOIN FETCH c.owners WHERE c.id = :fId", Car.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Car> findAll() {
        return crudRepository.query(
                "from Car c JOIN FETCH c.owners order by c.id", Car.class);
    }
}
