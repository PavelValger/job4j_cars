package ru.job4j.cars.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.cars.dto.PostCreating;
import ru.job4j.cars.dto.PostPreview;
import ru.job4j.cars.model.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "name", source = "engineName")
    Engine getEngineFromPostCreating(PostCreating postCreating);

    @Mapping(target = "startAt", source = "historyStartAt")
    @Mapping(target = "endAt", source = "historyEndAt")
    History getHistoryFromPostCreating(PostCreating postCreating);

    @Mapping(target = "before", source = "priceHistoryAfter")
    @Mapping(target = "after", source = "priceHistoryAfter")
    PriceHistory getPriceHistoryFromPostCreating(PostCreating postCreating);

    default Owner getOwnerFromPostCreating(PostCreating postCreating) {
        Owner owner = new Owner();
        owner.setName(postCreating.getOwnerName());
        owner.setHistory(getHistoryFromPostCreating(postCreating));
        return owner;
    }

    default Car getCarFromPostCreating(PostCreating postCreating) {
        Car car = new Car();
        car.setName(postCreating.getCarName());
        car.setEngine(getEngineFromPostCreating(postCreating));
        Owner owner = getOwnerFromPostCreating(postCreating);
        car.setOwner(owner);
        car.getOwners().add(owner);
        return car;
    }

    default Post getPostFromPostCreating(PostCreating postCreating, User user) {
        Post post = new Post();
        post.setDescription(postCreating.getPostDescription());
        post.setUser(user);
        post.getPriceHistories().add(getPriceHistoryFromPostCreating(postCreating));
        post.setCar(getCarFromPostCreating(postCreating));
        return post;
    }

    default PostPreview getPostPreviewFromEntities(Post post, PriceHistory priceHistory, Car car,
                                                   Engine engine, boolean status) {
        PostPreview postPreview = new PostPreview();
        postPreview.setId(post.getId());
        postPreview.setPrice(priceHistory.getAfter());
        postPreview.setCarName(car.getName());
        postPreview.setEngineName(engine.getName());
        postPreview.setStatus(status);
        Set<File> files = post.getFiles();
        for (File file : files) {
            postPreview.getFiles().add(file.getId());
        }
        return postPreview;
    }
}
