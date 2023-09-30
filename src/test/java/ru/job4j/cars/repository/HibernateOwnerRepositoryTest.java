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
import ru.job4j.cars.model.Owner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HibernateOwnerRepositoryTest {
    private static SessionFactory sf;
    private static HibernateOwnerRepository ownerRepository;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        ownerRepository = new HibernateOwnerRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete Owner")
                    .executeUpdate();
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

    private Owner getOwner() {
        Owner owner = new Owner();
        owner.setName("testOwner");
        owner.setHistory(getHistory());
        return owner;
    }

    @Test
    void whenSaveThenGetSame() {
        Owner owner = getOwner();
        assertThat(ownerRepository.save(owner).get().getName()).isEqualTo("testOwner");
    }

    @Test
    void whenFindByIdThenGetSame() {
        Owner owner = getOwner();
        ownerRepository.save(owner);
        assertThat(ownerRepository.findById(owner.getId()).get().getName()).isEqualTo("testOwner");
    }

    @Test
    void whenDeleteByIdThenTrue() {
        Owner owner = getOwner();
        ownerRepository.save(owner);
        assertThat(ownerRepository.deleteById(owner.getId())).isTrue();
    }

    @Test
    void whenFindAllThenGetSame() {
        Owner owner = getOwner();
        ownerRepository.save(owner);
        assertThat(ownerRepository.findAll()).isEqualTo(List.of(owner));
    }

    @Test
    void whenUpdateThenGetSame() {
        Owner owner = getOwner();
        ownerRepository.save(owner);
        var ownerUp = getOwner();
        ownerUp.setId(owner.getId());
        ownerUp.setName("OwnerUp");
        ownerRepository.update(ownerUp);
        assertThat(ownerRepository.findById(owner.getId()).get().getName()).isEqualTo("OwnerUp");
    }
}