package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    @Override
    public Post save(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    private Optional<Post> findPostPriceHistoriesById(int id) {
        return crudRepository
                .optional("FROM Post post LEFT JOIN FETCH post.priceHistories "
                                + "WHERE post.id = :fId", Post.class,
                        Map.of("fId", id)
                );
    }

    private Optional<Post> findPostFilesById(int id) {
        return crudRepository
                .optional("FROM Post post LEFT JOIN FETCH post.files "
                                + "WHERE post.id = :fId", Post.class,
                        Map.of("fId", id)
                );
    }

    @Override
    public Optional<Post> findById(int id) {
        Optional<Post> postPriceHistories = findPostPriceHistoriesById(id);
        Optional<Post> postFiles = findPostFilesById(id);
        if (postPriceHistories.isPresent() && postFiles.isPresent()) {
            postPriceHistories.get().setFiles(postFiles.get().getFiles());
        }
        return postPriceHistories;
    }

    private Collection<Post> findAllOnRequest(String query, Map<String, Object> objectMap) {
        Map<String, Object> map = new HashMap<>();
        List<Post> postsPriceHistories = crudRepository
                .query("SELECT DISTINCT post FROM Post post LEFT JOIN FETCH post.priceHistories "
                        + "order by post.created desc", Post.class);
        query = String.format("SELECT DISTINCT post from Post post "
                + "LEFT JOIN FETCH post.files WHERE post IN :fPosts %s order by post.created desc", query);
        map.put("fPosts", postsPriceHistories);
        map.putAll(objectMap);
        Collection<Post> postsFiles = crudRepository.query(query, Post.class, map);
        int index = 0;
        for (Post post : postsFiles) {
            post.setPriceHistories(postsPriceHistories.get(index).getPriceHistories());
            index++;
        }
        return postsFiles;
    }

    @Override
    public Collection<Post> findAll() {
        return findAllOnRequest("", Map.of());
    }

    @Override
    public boolean updateStatus(int id) {
        return crudRepository.numberRowsRequest(
                "UPDATE Post SET "
                        + "status = true "
                        + "WHERE id = :fId",
                Map.of("fId", id)
        ) > 0;
    }

    /**
     * Показать объявления определенной марки
     *
     * @param brand марка автомобиля
     * @return список объявлений указанной марки
     */
    @Override
    public Collection<Post> showBrand(String brand) {
        return findAllOnRequest("and post.car.name = :fBrand", Map.of("fBrand", brand));
    }

    @Override
    public Collection<Post> showBodywork(String bodywork) {
        return findAllOnRequest("and post.car.bodywork = :fBodywork", Map.of("fBodywork", bodywork));
    }

    @Override
    public Collection<Post> showEngine(String engine) {
        return findAllOnRequest("and post.car.engine.name = :fEngine", Map.of("fEngine", engine));
    }

    @Override
    public Collection<Post> showUserPost(Integer userId) {
        return findAllOnRequest("and post.user.id = :fUserId", Map.of("fUserId", userId));
    }

    @Override
    public Collection<Post> showSubscribe(Collection<Post> subscribe) {
        return findAllOnRequest("and post IN :fSubscribe", Map.of("fSubscribe", subscribe));
    }

    /**
     * Показать объявления за последний день
     *
     * @return список объявлений за последний день
     */
    public Collection<Post> showPostLastDay() {
        var localDateTime = LocalDateTime.now();
        return findAllOnRequest(
                "and post.created > :fLocalDate", Map.of("fLocalDate", localDateTime.minusDays(1)));
    }

    /**
     * Показать объявления с фото.
     *
     * @return список объявлений с фото.
     */
    public Collection<Post> showPostWithPhoto() {
        return findAllOnRequest("and post.files.size != 0", Map.of());
    }

    public boolean deleteById(int id) {
        return crudRepository.numberRowsRequest(
                "delete Post where id = :fId",
                Map.of("fId", id)
        ) > 0;
    }
}
