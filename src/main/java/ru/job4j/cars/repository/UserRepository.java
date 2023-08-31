package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Зафиксировать изменения и закрыть сессию.
     *
     * @param session сессия.
     */
    private void close(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Сессия для операций без возвращаемого значения.
     *
     * @param consumer применяет действие.
     */
    private void crud(Consumer<Session> consumer) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            consumer.accept(session);
            close(session);
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Сессия для операций с возвращаемым значением.
     *
     * @param function преобразование входных параметров.
     * @param <T>      тип вычисления функции.
     * @return результат вычисления функции.
     */
    private <T> T search(Function<Session, T> function) {
        Session session = sf.openSession();
        T result = null;
        try {
            session.beginTransaction();
            result = function.apply(session);
            close(session);
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return result;
    }

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        crud(session -> session.save(user));
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        crud(session -> session.update(user));
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        crud(session -> session
                .createQuery("DELETE User WHERE id = :fId")
                .setParameter("fId", userId)
                .executeUpdate());
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return new ArrayList<>(search(session -> session.createQuery("from User", User.class).list()));
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        return search(session -> Optional.ofNullable(session.get(User.class, userId)));
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return new ArrayList<>(search(session -> session
                .createQuery("from User where login like :fKey", User.class)
                .setParameter("fKey", String.format("%%%s%%", key))
                .list()));
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        return search(session -> session
                .createQuery("from User as i where i.login = :fLogin", User.class)
                .setParameter("fLogin", login)
                .uniqueResultOptional());
    }
}
