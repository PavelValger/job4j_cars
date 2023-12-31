package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateCarRepository.class.getName());
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            LOG.info("Регистрация зарегистрированного пользователя, Exception in log example", e);
        }
        return Optional.empty();
    }

    /**
     * Поиск пользователя по логину и паролю
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return Optional User
     */
    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "from User u "
                        + "where u.login = :fLogin and u.password = :fPassword", User.class,
                Map.of("fLogin", login, "fPassword", password)
        );
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(int userId) {
        return crudRepository.optional(
                "from User u LEFT JOIN FETCH u.participates p LEFT JOIN FETCH u.owners "
                        + "where u.id = :fId", User.class,
                Map.of("fId", userId)
        );
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    @Override
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * Найти пользователя по ID без полей participates и owners
     *
     * @param userId id пользователя
     * @return пользователь
     */
    public Optional<User> findOnlyUserById(int userId) {
        return crudRepository.optional(
                "from User u "
                        + "where u.id = :fId", User.class,
                Map.of("fId", userId)
        );
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public boolean delete(int userId) {
        return crudRepository.numberRowsRequest(
                "delete User where id = :fId",
                Map.of("fId", userId)
        ) > 0;
    }

    /**
     * Список пользователей, отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return crudRepository.query(
                "from User u JOIN FETCH u.participates JOIN FETCH u.owners order by u.id asc", User.class);
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                "from User u JOIN FETCH u.participates JOIN FETCH u.owners where u.login like :fKey",
                User.class, Map.of("fKey", String.format("%%%s%%", key))
        );
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                "from User u JOIN FETCH u.participates JOIN FETCH u.owners where u.login = :fLogin",
                User.class, Map.of("fLogin", login)
        );
    }
}
