package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.History;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HibernateHistoryRepositoryTest {
    private static SessionFactory sf;
    private static HibernateHistoryRepository historyRepository;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        historyRepository = new HibernateHistoryRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete History")
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    private History getHistory() {
        History history = new History();
        history.setStartAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        history.setEndAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return history;
    }

    @Test
    void whenSaveThenGetSame() {
        History history = getHistory();
        assertThat(historyRepository.save(history).get()).isEqualTo(history);
    }

    @Test
    void whenFindByIdThenGetSame() {
        History history = getHistory();
        historyRepository.save(history);
        assertThat(historyRepository.findById(history.getId()).get()).isEqualTo(history);
    }

    @Test
    void whenDeleteByIdThenTrue() {
        History history = getHistory();
        historyRepository.save(history);
        assertThat(historyRepository.deleteById(history.getId())).isTrue();
    }

    @Test
    void whenFindAllThenGetSame() {
        History history = getHistory();
        historyRepository.save(history);
        assertThat(historyRepository.findAll()).isEqualTo(List.of(history));
    }

    @Test
    void whenUpdateThenGetSame() {
        History history = getHistory();
        historyRepository.save(history);
        var historyUp = getHistory();
        var date = LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.SECONDS);
        historyUp.setId(history.getId());
        historyUp.setStartAt(date);
        historyRepository.update(historyUp);
        assertThat(historyRepository.findById(history.getId()).get().getStartAt()).isEqualTo(date);
    }
}