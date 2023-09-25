package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateCarRepository.class.getName());
    private final CrudRepository crudRepository;

    @Override
    public Optional<Post> save(Post post) {
        try {
            crudRepository.run(session -> session.persist(post));
            return Optional.of(post);
        } catch (Exception e) {
            LOG.info("Неудачная попытка сохранения объявления, Exception in log example", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.numberRowsRequest(
                "delete Post where id = :fId",
                Map.of("fId", id)
        ) > 0;
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "FROM Post p JOIN FETCH p.messengers WHERE p.id = :fId", Post.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Post> findAll() {
        return crudRepository.query(
                "from Post p JOIN FETCH p.messengers order by p.id", Post.class);
    }

    /**
     * Показать объявления за последний день
     *
     * @return список объявлений за последний день
     */
    public Collection<Post> showPostLastDay() {
        return crudRepository.query(
                "FROM Post p JOIN FETCH p.messengers WHERE p.created > current_date", Post.class);
    }

    /**
     * Показать объявления с фото.
     *
     * @return список объявлений с фото.
     */
    public Collection<Post> showPostWithPhoto() {
        return crudRepository.query(
                "FROM Post p JOIN FETCH p.messengers WHERE p.file is not null", Post.class);
    }

    /**
     * Показать объявления определенной марки
     * @param brand марка автомобиля
     * @return список объявлений указанной марки
     */
    public Collection<Post> showBrand(String brand) {
        return crudRepository.query(
                "FROM Post p JOIN FETCH p.messengers WHERE p.car.name like :fBrand", Post.class,
                Map.of("fBrand", String.format("%%%s%%", brand)));
    }
}
