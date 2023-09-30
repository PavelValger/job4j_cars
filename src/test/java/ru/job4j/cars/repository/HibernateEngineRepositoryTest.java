package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HibernateEngineRepositoryTest {
    private static SessionFactory sf;
    private static HibernateEngineRepository engineRepository;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        engineRepository = new HibernateEngineRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete Engine")
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    private Engine getEngine() {
        Engine engine = new Engine();
        engine.setName("test Engine");
        return engine;
    }

    @Test
    void whenSaveThenGetSame() {
        Engine engine = getEngine();
        assertThat(engineRepository.save(engine).get().getName()).isEqualTo("test Engine");
    }

    @Test
    void whenFindByIdThenGetSame() {
        Engine engine = getEngine();
        engineRepository.save(engine);
        assertThat(engineRepository.findById(engine.getId()).get().getName()).isEqualTo("test Engine");
    }

    @Test
    void whenDeleteByIdThenTrue() {
        Engine engine = getEngine();
        engineRepository.save(engine);
        assertThat(engineRepository.deleteById(engine.getId())).isTrue();
    }

    @Test
    void whenFindAllThenGetSame() {
        Engine engine = getEngine();
        engineRepository.save(engine);
        assertThat(engineRepository.findAll()).isEqualTo(List.of(engine));
    }

    @Test
    void whenUpdateThenGetSame() {
        Engine engine = getEngine();
        engineRepository.save(engine);
        var engineUp = getEngine();
        engineUp.setId(engine.getId());
        engineUp.setName("Car up");
        engineRepository.update(engineUp);
        assertThat(engineRepository.findById(engine.getId()).get().getName()).isEqualTo("Car up");
    }
}