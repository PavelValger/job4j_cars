package ru.job4j.cars.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.OwnerCreating;
import ru.job4j.cars.dto.PostCreating;
import ru.job4j.cars.dto.PostPreview;
import ru.job4j.cars.mappers.OwnerMapper;
import ru.job4j.cars.mappers.PostMapper;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.PostRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimplePostService implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final OwnerMapper ownerMapper;
    private final FileService fileService;
    private final UserService userService;
    private final CarService carService;

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
    public void update(Post post, List<FileDto> images) {
        post.getFiles().addAll(images.stream().map(fileService::getFileFromFileDto).toList());
        postRepository.update(post);
    }

    @Override
    public void updatePrice(Post post, Integer newPrice) {
        var histories = post.getPriceHistories();
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(histories.get(histories.size() - 1).getAfter());
        priceHistory.setAfter(newPrice);
        histories.add(priceHistory);
        postRepository.update(post);
    }

    @Override
    public void updateOwner(Post post, OwnerCreating ownerCreating) {
        var optionalCar = carService.findById(post.getCar().getId());
        if (optionalCar.isPresent()) {
            var car = optionalCar.get();
            car.getOwners().add(ownerMapper.getOwnerFromOwnerCreating(ownerCreating));
            carService.update(car);
        }
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    private Collection<PostPreview> getPostsPreview(Collection<Post> collection) {
        return collection.stream()
                .map(postMapper::getPostPreviewFromPost).collect(Collectors.toList());
    }

    @Override
    public Collection<PostPreview> findAll() {
        return getPostsPreview(postRepository.findAll());
    }

    @Override
    public boolean updateStatus(int id) {
        return postRepository.updateStatus(id);
    }

    @Override
    public Collection<PostPreview> showBrand(String brand) {
        return getPostsPreview(postRepository.showBrand(brand));
    }

    @Override
    public Collection<PostPreview> showBodywork(String bodywork) {
        return getPostsPreview(postRepository.showBodywork(bodywork));
    }

    @Override
    public Collection<PostPreview> showEngine(String engine) {
        return getPostsPreview(postRepository.showEngine(engine));
    }

    @Override
    public Collection<PostPreview> showUserPost(Integer userId) {
        return getPostsPreview(postRepository.showUserPost(userId));
    }

    @Override
    public Collection<PostPreview> showSubscribe(Collection<Post> subscribe) {
        return getPostsPreview(postRepository.showSubscribe(subscribe));
    }
}
