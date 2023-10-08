package ru.job4j.cars.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostCreating;
import ru.job4j.cars.dto.PostPreview;
import ru.job4j.cars.mappers.PostMapper;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.PostRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimplePostService implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final FileService fileService;
    private final UserService userService;

    @Override
    public Optional<Post> save(PostCreating postCreating, User user, FileDto image) {
        var file = fileService.getFileFromFileDto(image);
        var post = postMapper.getPostFromPostCreating(postCreating);
        post.getFiles().add(file);
        post.setUser(user);
        var postSaving = postRepository.save(post);
        var optionalUser = userService.findById(user.getId());
        if (postSaving.isPresent() && optionalUser.isPresent()) {
            var owner = postSaving.get().getCar().getOwner();
            user = optionalUser.get();
            user.getOwners().add(owner);
            userService.update(user);
        }
        return postSaving;
    }

    @Override
    public void update(Post post, FileDto image) {
        var file = fileService.getFileFromFileDto(image);
        post.getFiles().add(file);
        postRepository.update(post);
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public Collection<PostPreview> findAll() {
        return postRepository.findAll().stream()
                .map(postMapper::getPostPreviewFromEntities).collect(Collectors.toList());
    }

    @Override
    public Collection<Post> showBrand(String brand) {
        return postRepository.showBrand(brand);
    }

    @Override
    public boolean updateStatus(int id) {
        return postRepository.updateStatus(id);
    }
}