package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateCarRepositoryTest {
    private static SessionFactory sf;
    private static HibernateCarRepository hibernateCarRepository;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernateCarRepository = new HibernateCarRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete Car")
                    .executeUpdate();
            session.createQuery(
                            "delete Engine")
                    .executeUpdate();
            session.createQuery(
                            "delete Owner")
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    private Owner getOwner() {
        Owner owner = new Owner();
        owner.setName("testOwner");
        return owner;
    }

    private List<Owner> getOwners() {
        Owner owner = new Owner();
        owner.setName("former owner");
        return List.of(owner);
    }

    private Engine getEngine() {
        Engine engine = new Engine();
        engine.setName("testEngine");
        return engine;
    }

    private Car getCar() {
        Car car = new Car();
        car.setName("Car test");
        car.setEngine(getEngine());
        car.setOwner(getOwner());
        car.setOwners(getOwners());
        car.setBodywork("body");
        return car;
    }

    @Test
    void whenSaveThenGetSame() {
        var car = getCar();
        assertThat(hibernateCarRepository.save(car).get().getName()).isEqualTo("Car test");
    }

    @Test
    void whenDeleteByIdThenTrue() {
        var car = getCar();
        hibernateCarRepository.save(car);
        assertThat(hibernateCarRepository.deleteById(car.getId())).isTrue();
    }

    @Test
    void whenUpdateThenGetSame() {
        var car = getCar();
        hibernateCarRepository.save(car);
        var carUp = getCar();
        carUp.setId(car.getId());
        carUp.setName("Car up");
        hibernateCarRepository.update(carUp);
        assertThat(hibernateCarRepository.findById(car.getId()).get().getName()).isEqualTo("Car up");
    }

    @Test
    void whenFindByIdThenGetSame() {
        var car = getCar();
        hibernateCarRepository.save(car);
        assertThat(hibernateCarRepository.findById(car.getId()).get().getName()).isEqualTo("Car test");
    }

    @Test
    void whenFindAllThenGetSame() {
        var car = getCar();
        hibernateCarRepository.save(car);
        assertThat(hibernateCarRepository.findAll()).isEqualTo(List.of(car));
    }
}