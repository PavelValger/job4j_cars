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

class HibernatePostRepositoryTest {
    private static SessionFactory sf;
    private static HibernatePostRepository hibernatePostRepository;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernatePostRepository = new HibernatePostRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clearAll() {
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

    private User getUser() {
        HibernateUserRepository hub = new HibernateUserRepository(new CrudRepository(sf));
        User user = new User();
        user.setLogin("qwerty");
        user.setPassword("qwerty2");
        hub.save(user);
        return user;
    }

    private User getUserForUpdate() {
        HibernateUserRepository hub = new HibernateUserRepository(new CrudRepository(sf));
        User user = new User();
        user.setLogin("qwertyUp");
        user.setPassword("qwerty2Up");
        hub.save(user);
        return user;
    }

    private List<PriceHistory> getPriceHistory() {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setAfter(1000000);
        priceHistory.setBefore(900000);
        return List.of(priceHistory);
    }

    private Car getCar() {
        Car car = new Car();
        car.setName("lada");
        car.setBodywork("body");
        return car;
    }

    private Set<File> getFiles() {
        File file = new File();
        file.setName("test");
        file.setPath("test");
        return Set.of(file);
    }

    private Set<File> getFilesForUpdate() {
        File file = new File();
        file.setName("testUp");
        file.setPath("testUp");
        return Set.of(file);
    }

    private Post getPost() {
        Post post = new Post();
        post.setDescription("test");
        post.setStatus(false);
        post.setCar(getCar());
        post.setFiles(getFiles());
        post.setUser(getUser());
        post.setPriceHistories(getPriceHistory());
        return post;
    }

    private Post getPostForUpdate() {
        Post post = new Post();
        post.setDescription("test update");
        post.setCar(getCar());
        post.setFiles(getFilesForUpdate());
        post.setUser(getUserForUpdate());
        post.setPriceHistories(getPriceHistory());
        return post;
    }

    @Test
    void whenSavePostThenPost() {
        Post post = getPost();
        assertThat(hibernatePostRepository.save(post).get()).isEqualTo(post);
    }

    @Test
    void whenDeleteByIdThenTrue() {
        Post post = new Post();
        post.setDescription("test");
        post.setCar(getCar());
        post.setUser(getUser());
        hibernatePostRepository.save(post);
        assertThat(hibernatePostRepository.deleteById(post.getId())).isTrue();
    }

    @Test
    void whenDeleteByIdThenFalse() {
        assertThat(hibernatePostRepository.deleteById(100500)).isFalse();
    }

    @Test
    void whenUpdateThenSuccessfully() {
        Post post = getPost();
        hibernatePostRepository.save(post);
        Post postUp = getPostForUpdate();
        postUp.setId(post.getId());
        hibernatePostRepository.update(postUp);
        assertThat(hibernatePostRepository.findById(post.getId()).get().getDescription())
                .isEqualTo("test update");
    }

    @Test
    void whenFindByIdPostThenPost() {
        Post post = getPost();
        hibernatePostRepository.save(post);
        assertThat(hibernatePostRepository.findById(post.getId()).get()).isEqualTo(post);
    }

    @Test
    void whenFindAllThenCollectionPost() {
        Post post = getPost();
        hibernatePostRepository.save(post);
        assertThat(hibernatePostRepository.findAll()).isEqualTo(List.of(post));
    }

    @Test
    void whenShowPostLastDayThenPost() {
        Post post = getPost();
        hibernatePostRepository.save(post);
        assertThat(hibernatePostRepository.showPostLastDay()).isEqualTo(List.of(post));
    }

    @Test
    void whenShowPostWithPhotoThenPost() {
        Post post = getPost();
        var optionalPost = hibernatePostRepository.save(post);
        assertThat(hibernatePostRepository.showPostWithPhoto()).isEqualTo(List.of(optionalPost.get()));
    }

    @Test
    void whenShowBrandThenPost() {
        Post post = getPost();
        hibernatePostRepository.save(post);
        assertThat(hibernatePostRepository.showBrand("lada")).isEqualTo(List.of(post));
    }
}