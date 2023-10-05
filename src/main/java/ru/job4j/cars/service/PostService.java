package ru.job4j.cars.service;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostCreating;
import ru.job4j.cars.dto.PostPreview;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.util.Collection;
import java.util.Optional;

public interface PostService {

    Optional<Post> save(PostCreating postCreating, User user, FileDto image);

    void update(Post post);

    Optional<Post> findById(int id);

    Collection<PostPreview> findAll();

    Collection<Post> showBrand(String brand);
}
