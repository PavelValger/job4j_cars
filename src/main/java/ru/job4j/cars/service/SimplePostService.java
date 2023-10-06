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

    @Override
    public Optional<Post> save(PostCreating postCreating, User user, FileDto image) {
        var file = fileService.getFileFromFileDto(image);
        return postRepository.save(postMapper.getPostFromPostCreating(postCreating, user, file));
    }

    @Override
    public void update(Post post) {
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
}
