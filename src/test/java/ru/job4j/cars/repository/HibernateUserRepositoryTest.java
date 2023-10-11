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

import static org.assertj.core.api.Assertions.*;

class HibernateUserRepositoryTest {
    private static SessionFactory sf;
    private static HibernateUserRepository hibernateUserRepository;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernateUserRepository = new HibernateUserRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete Participant")
                    .executeUpdate();
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
                            "delete Owner")
                    .executeUpdate();
            session.createQuery(
                            "delete User")
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    private User getUser() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        return user;
    }

    private Post getPost() {
        HibernatePostRepository postRepository = new HibernatePostRepository(new CrudRepository(sf));
        Post post = new Post();
        post.setDescription("test");
        User userPost = new User();
        userPost.setLogin("testUserPost");
        userPost.setPassword("testUserPost");
        hibernateUserRepository.save(userPost);
        post.setUser(userPost);
        File file = new File();
        file.setName("test");
        file.setPath("test");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setAfter(1000000);
        priceHistory.setBefore(900000);
        post.setFiles(List.of(file));
        post.setPriceHistories(List.of(priceHistory));
        postRepository.save(post);
        return postRepository.findById(post.getId()).get();
    }

    private Owner getOwner() {
        HibernateOwnerRepository hor = new HibernateOwnerRepository(new CrudRepository(sf));
        Owner owner = new Owner();
        owner.setName("test");
        hor.save(owner);
        return owner;
    }

    @Test
    public void whenSaveThenGetSame() {
        var user = getUser();
        assertThat(hibernateUserRepository.save(user).get()).isEqualTo(user);
    }

    @Test
    public void whenSaveAndSaveThenIsEmpty() {
        User userCopy = new User();
        userCopy.setLogin("test");
        userCopy.setPassword("test");
        hibernateUserRepository.save(getUser());
        assertThat(hibernateUserRepository.save(userCopy)).isEmpty();
    }

    @Test
    void whenFindByLoginAndPasswordThenUser() {
        var user = getUser();
        var post = getPost();
        var owner = getOwner();
        user.setParticipates(Set.of(post));
        user.setOwners(Set.of(owner));
        hibernateUserRepository.save(user);
        assertThat(hibernateUserRepository
                .findByLoginAndPassword("test", "test").get())
                .isEqualTo(user);
    }

    @Test
    public void whenSaveAndGetInvalidPasswordThenIsEmpty() {
        var user = getUser();
        var post = getPost();
        var owner = getOwner();
        user.setParticipates(Set.of(post));
        user.setOwners(Set.of(owner));
        hibernateUserRepository.save(user);
        var findUser = hibernateUserRepository.findByLoginAndPassword("test", "12345");
        assertThat(findUser.isEmpty()).isTrue();
    }

    @Test
    public void whenUpdateUserThenSuccessfully() {
        var user = getUser();
        var post = getPost();
        var owner = getOwner();
        user.setParticipates(Set.of(post));
        user.setOwners(Set.of(owner));
        hibernateUserRepository.save(user);
        User userUp = new User();
        userUp.setId(user.getId());
        userUp.setLogin("testUp");
        userUp.setPassword("testUp");
        userUp.setParticipates(Set.of(post));
        userUp.setOwners(Set.of(owner));
        hibernateUserRepository.update(userUp);
        assertThat(hibernateUserRepository.findById(user.getId()).get().getPassword()).isEqualTo("testUp");
    }

    @Test
    void whenFindByIdThenUser() {
        var user = getUser();
        var post = getPost();
        var owner = getOwner();
        user.setParticipates(Set.of(post));
        user.setOwners(Set.of(owner));
        hibernateUserRepository.save(user);
        assertThat(hibernateUserRepository.findById(user.getId()).get()).isEqualTo(user);
    }

    @Test
    void whenFindOnlyUserByIdThenUser() {
        var user = getUser();
        var post = getPost();
        var owner = getOwner();
        user.setParticipates(Set.of(post));
        user.setOwners(Set.of(owner));
        hibernateUserRepository.save(user);
        assertThat(hibernateUserRepository.findOnlyUserById(user.getId()).get()).isEqualTo(user);
    }

    @Test
    void whenDeleteThenTrue() {
        var user = getUser();
        hibernateUserRepository.save(user);
        assertThat(hibernateUserRepository.delete(user.getId())).isTrue();
    }
}