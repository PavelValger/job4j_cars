package ru.job4j.cars.service;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.OwnerCreating;
import ru.job4j.cars.dto.PostCreating;
import ru.job4j.cars.dto.PostPreview;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PostService {

    Optional<Post> save(PostCreating postCreating, User user, FileDto image);

    void update(Post post, List<FileDto> images);

    Optional<Post> findById(int id);

    Collection<PostPreview> findAll();

    boolean updateStatus(int id);

    void updatePrice(Post post, Integer newPrice);

    void updateOwner(Post post, OwnerCreating ownerCreating);

    Collection<PostPreview> showBrand(String brand);

    Collection<PostPreview> showBodywork(String bodywork);

    Collection<PostPreview> showEngine(String engine);

    Collection<PostPreview> showUserPost(Integer userId);

    Collection<PostPreview> showSubscribe(Collection<Post> subscribe);
}
