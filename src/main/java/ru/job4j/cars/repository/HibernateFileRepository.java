package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateFileRepository implements FileRepository {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateCarRepository.class.getName());
    private final CrudRepository crudRepository;

    @Override
    public Optional<File> save(File file) {
        try {
            crudRepository.run(session -> session.persist(file));
            return Optional.of(file);
        } catch (Exception e) {
            LOG.info("Неудачная попытка сохранения файла, Exception in log example", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<File> findById(int id) {
        return crudRepository.optional(
                "FROM File WHERE id = :fId", File.class,
                Map.of("fId", id)
        );
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.numberRowsRequest(
                "delete File where id = :fId",
                Map.of("fId", id)
        ) > 0;
    }

    @Override
    public Collection<File> findAll() {
        return crudRepository.query(
                "from File order by id", File.class);
    }
}
