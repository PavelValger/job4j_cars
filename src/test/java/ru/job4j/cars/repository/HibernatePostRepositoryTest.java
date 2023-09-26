package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class HibernatePostRepositoryTest {
    private static SessionFactory sf;
    private static HibernatePostRepository hibernatePostRepository;
    private static User user;
    private static Car car;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernatePostRepository = new HibernatePostRepository(new CrudRepository(sf));
        HibernateUserRepository hub = new HibernateUserRepository(new CrudRepository(sf));
        user = new User();
        user.setLogin("qwerty");
        user.setPassword("qwerty2");
        hub.save(user);
        HibernateCarRepository hibernateCarRepository = new HibernateCarRepository(new CrudRepository(sf));
        car = new Car();
        car.setName("lada");
        hibernateCarRepository.save(car);
    }

    @AfterEach
    public void clearPost() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete PriceHistory")
                    .executeUpdate();
            session.createQuery(
                            "delete File")
                    .executeUpdate();
            session.createQuery(
                            "delete Post")
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @AfterAll
    public static void clearAll() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete User")
                    .executeUpdate();
            session.createQuery(
                            "delete Car")
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Test
    void whenSavePostThenPost() {
        File file = new File();
        file.setName("test");
        file.setPath("test");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setAfter(1000000);
        priceHistory.setBefore(900000);
        Post post = new Post();
        post.setDescription("test");
        post.setCar(car);
        post.setFiles(Set.of(file));
        post.setUser(user);
        post.setMessengers(List.of(priceHistory));
        assertThat(hibernatePostRepository.save(post).get()).isEqualTo(post);
    }

    @Test
    void whenFindByIdPostThenPost() {
        File file = new File();
        file.setName("test");
        file.setPath("test");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setAfter(1000000);
        priceHistory.setBefore(900000);
        Post post = new Post();
        post.setDescription("test");
        post.setCar(car);
        post.setFiles(Set.of(file));
        post.setUser(user);
        post.setMessengers(List.of(priceHistory));
        hibernatePostRepository.save(post);
        assertThat(hibernatePostRepository.findById(post.getId()).get()).isEqualTo(post);
    }

    @Test
    void whenShowPostLastDayThenPost() {
        File file = new File();
        file.setName("test");
        file.setPath("test");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setAfter(1000000);
        priceHistory.setBefore(900000);
        Post post = new Post();
        post.setDescription("test");
        post.setCar(car);
        post.setFiles(Set.of(file));
        post.setUser(user);
        post.setMessengers(List.of(priceHistory));
        hibernatePostRepository.save(post);
        assertThat(hibernatePostRepository.showPostLastDay()).isEqualTo(List.of(post));
    }

    @Test
    void whenShowPostWithPhotoThenPost() {
        File file = new File();
        file.setName("test");
        file.setPath("test");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setAfter(1000000);
        priceHistory.setBefore(900000);
        Post post = new Post();
        post.setDescription("test");
        post.setCar(car);
        post.setFiles(Set.of(file));
        post.setUser(user);
        post.setMessengers(List.of(priceHistory));
        var optionalPost = hibernatePostRepository.save(post);
        assertThat(hibernatePostRepository.showPostWithPhoto()).isEqualTo(List.of(optionalPost.get()));
    }

    @Test
    void whenShowBrandThenPost() {
        File file = new File();
        file.setName("test");
        file.setPath("test");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setAfter(1000000);
        priceHistory.setBefore(900000);
        Post post = new Post();
        post.setDescription("test");
        post.setCar(car);
        post.setFiles(Set.of(file));
        post.setUser(user);
        post.setMessengers(List.of(priceHistory));
        hibernatePostRepository.save(post);
        assertThat(hibernatePostRepository.showBrand("lada")).isEqualTo(List.of(post));
    }
}