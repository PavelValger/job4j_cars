package ru.job4j.cars.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.cars.dto.PostCreating;
import ru.job4j.cars.dto.PostPreview;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.TimeZone;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "name", source = "engineName")
    Engine getEngineFromPostCreating(PostCreating postCreating);

    @Mapping(target = "before", source = "priceHistoryAfter")
    @Mapping(target = "after", source = "priceHistoryAfter")
    PriceHistory getPriceHistoryFromPostCreating(PostCreating postCreating);

    default History getHistoryFromPostCreating(PostCreating postCreating) {
        History history = new History();
        history.setEndAt(postCreating.getHistoryEndAt());
        history.setStartAt(LocalDateTime
                .ofInstant(postCreating.getHistoryStartAt().toInstant(), TimeZone.getDefault().toZoneId()));
        return history;
    }

    default Owner getOwnerFromPostCreating(PostCreating postCreating) {
        Owner owner = new Owner();
        owner.setName(postCreating.getOwnerName());
        owner.setHistory(getHistoryFromPostCreating(postCreating));
        return owner;
    }

    default Car getCarFromPostCreating(PostCreating postCreating) {
        Car car = new Car();
        car.setName(postCreating.getCarName());
        car.setBodywork(postCreating.getBodywork());
        car.setEngine(getEngineFromPostCreating(postCreating));
        Owner owner = getOwnerFromPostCreating(postCreating);
        car.setOwner(owner);
        car.getOwners().add(owner);
        return car;
    }

    default Post getPostFromPostCreating(PostCreating postCreating) {
        Post post = new Post();
        post.setDescription(postCreating.getPostDescription());
        post.getPriceHistories().add(getPriceHistoryFromPostCreating(postCreating));
        post.setCar(getCarFromPostCreating(postCreating));
        return post;
    }

    default PostPreview getPostPreviewFromPost(Post post) {
        PostPreview postPreview = new PostPreview();
        postPreview.setId(post.getId());
        postPreview.setPrice(post.getPriceHistories().get(post.getPriceHistories().size() - 1).getAfter());
        postPreview.setCarName(post.getCar().getName());
        postPreview.setBodywork(post.getCar().getBodywork());
        postPreview.setEngineName(post.getCar().getEngine().getName());
        postPreview.setStatus(post.isStatus());
        postPreview.setUserLogin(post.getUser().getLogin());
        postPreview.setFileId(post.getFiles().get(0).getId());
        return postPreview;
    }
}