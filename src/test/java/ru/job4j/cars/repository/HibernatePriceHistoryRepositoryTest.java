package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HibernatePriceHistoryRepositoryTest {
    private static SessionFactory sf;
    private static HibernatePriceHistoryRepository priceHistoryRepository;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        priceHistoryRepository = new HibernatePriceHistoryRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete PriceHistory")
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    private PriceHistory getPriceHistory() {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(500000);
        priceHistory.setAfter(450000);
        return priceHistory;
    }

    @Test
    void whenSaveThenGetSame() {
        PriceHistory priceHistory = getPriceHistory();
        assertThat(priceHistoryRepository.save(priceHistory).get().getBefore()).isEqualTo(500000);
    }

    @Test
    void whenFindByIdThenGetSame() {
        PriceHistory priceHistory = getPriceHistory();
        priceHistoryRepository.save(priceHistory);
        assertThat(priceHistoryRepository.findById(priceHistory.getId())
                .get().getBefore()).isEqualTo(500000);
    }

    @Test
    void whenDeleteByIdThenTrue() {
        PriceHistory priceHistory = getPriceHistory();
        priceHistoryRepository.save(priceHistory);
        assertThat(priceHistoryRepository.deleteById(priceHistory.getId())).isTrue();
    }

    @Test
    void whenFindAllThenGetSame() {
        PriceHistory priceHistory = getPriceHistory();
        priceHistoryRepository.save(priceHistory);
        assertThat(priceHistoryRepository.findAll()).isEqualTo(List.of(priceHistory));
    }

    @Test
    void whenUpdateThenGetSame() {
        PriceHistory priceHistory = getPriceHistory();
        priceHistoryRepository.save(priceHistory);
        var priceHistoryUp = getPriceHistory();
        priceHistoryUp.setId(priceHistory.getId());
        priceHistoryUp.setBefore(600000);
        priceHistoryRepository.update(priceHistoryUp);
        assertThat(priceHistoryRepository.findById(priceHistory.getId()).get()
                .getBefore()).isEqualTo(600000);
    }
}