package ru.job4j.cars.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimplePostService implements PostService {
    private final PostRepository postRepository;

    @Override
    public Optional<Post> save(Post post) {
        return postRepository.save(post);
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
    public Collection<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Collection<Post> showBrand(String brand) {
        return postRepository.showBrand(brand);
    }
}
