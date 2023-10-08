package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostRepository {

    Optional<Post> save(Post post);

    void update(Post post);

    Optional<Post> findById(int id);

    Collection<Post> findAll();

    Collection<Post> showBrand(String brand);

    boolean updateStatus(int id);
}
