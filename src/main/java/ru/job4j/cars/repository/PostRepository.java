package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    void update(Post post);

    Optional<Post> findById(int id);

    Collection<Post> findAll();

    boolean updateStatus(int id);

    Collection<Post> showBrand(String brand);

    Collection<Post> showBodywork(String bodywork);

    Collection<Post> showEngine(String engine);

    Collection<Post> showUserPost(Integer userId);

    Collection<Post> showSubscribe(Collection<Post> subscribe);
}
