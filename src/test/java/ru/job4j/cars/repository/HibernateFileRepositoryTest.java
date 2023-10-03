package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.File;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HibernateFileRepositoryTest {
    private static SessionFactory sf;
    private static HibernateFileRepository fileRepository;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        fileRepository = new HibernateFileRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete File")
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    private File getFile() {
        File file = new File();
        file.setName("test file");
        file.setPath("test path");
        return file;
    }

    @Test
    void whenSaveThenGetSame() {
        File file = getFile();
        assertThat(fileRepository.save(file).get().getName()).isEqualTo("test file");
    }

    @Test
    void whenFindByIdThenGetSame() {
        File file = getFile();
        fileRepository.save(file);
        assertThat(fileRepository.findById(file.getId()).get().getName()).isEqualTo("test file");
    }

    @Test
    void whenDeleteByIdThenTrue() {
        File file = getFile();
        fileRepository.save(file);
        assertThat(fileRepository.deleteById(file.getId())).isTrue();
    }

    @Test
    void whenFindAllThenGetSame() {
        File file = getFile();
        fileRepository.save(file);
        assertThat(fileRepository.findAll(List.of(file.getId()))).isEqualTo(List.of(file));
    }
}