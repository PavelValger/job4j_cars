package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.OwnerCreating;
import ru.job4j.cars.dto.PostCreating;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.CarService;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CarService carService;
    private final UserService userService;

    /**
     * Вывезти все объявления
     */
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts/list";
    }

    /**
     * Страница подачи объявления
     */
    @GetMapping("/create")
    public String getCreationPage() {
        return "posts/create";
    }

    /**
     * Отправка формы создания объявления
     */
    @PostMapping("/create")
    public String create(@ModelAttribute PostCreating postCreating,
                         HttpSession session, @RequestParam MultipartFile file, Model model) {
        var user = (User) session.getAttribute("user");
        try {
            var optionalPost = postService.save(postCreating, user,
                    new FileDto(file.getOriginalFilename(), file.getBytes()));
            return String.format("redirect:/posts/edit/%s", optionalPost.get().getId());
        } catch (NoSuchElementException | IOException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    /**
     * Страница редактирования объявления
     */
    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            return "errors/404";
        }
        model.addAttribute("post", postOptional.get());
        return "posts/edit";
    }

    /**
     * Обработка формы редактирования объявления
     */
    @PostMapping("/edit/{id}")
    public String update(Model model, @PathVariable int id, @RequestParam MultipartFile[] files) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            return "errors/404";
        }
        List<FileDto> images = new LinkedList<>();
        for (MultipartFile file : files) {
            try {
                images.add(new FileDto(file.getOriginalFilename(), file.getBytes()));
            } catch (IOException e) {
                model.addAttribute("message", e.getMessage());
                return "errors/500";
            }
        }
        postService.update(postOptional.get(), images);
        return String.format("redirect:/posts/edit/%s", postOptional.get().getId());
    }

    /**
     * Изменить стоимость
     */
    @PostMapping("/editPrice/{id}")
    public String updatePrice(Model model, @PathVariable int id, @RequestParam Integer newPrice) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            return "errors/404";
        }
        postService.updatePrice(postOptional.get(), newPrice);
        return String.format("redirect:/posts/edit/%s", postOptional.get().getId());
    }

    /**
     * Добавить владельца авто
     */
    @PostMapping("/editOwner/{id}")
    public String updateOwner(Model model, @PathVariable int id, @ModelAttribute OwnerCreating ownerCreating) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            return "errors/404";
        }
        postService.updateOwner(postOptional.get(), ownerCreating);
        return String.format("redirect:/posts/edit/%s", postOptional.get().getId());
    }

    /**
     * Сменить статус поста на продан
     */
    @GetMapping("/statusSold/{id}")
    public String updateStatus(Model model, @PathVariable int id) {
        var isUpdated = postService.updateStatus(id);
        if (!isUpdated) {
            model.addAttribute("message", "Объявление не найдено");
            return "errors/404";
        }
        return "redirect:/posts";
    }

    /**
     *
     * @return страницу подробного просмотра объявления
     */
    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            return "errors/404";
        }
        Post post = postOptional.get();
        model.addAttribute("post", post);
        model.addAttribute("car", carService.findById(post.getCar().getId()).get());
        model.addAttribute("price",
                post.getPriceHistories().get(post.getPriceHistories().size() - 1).getAfter());
        return "posts/one";
    }

    /**
     *
     * @return посты по марке авто
     */
    @PostMapping("/brandSearch")
    public String brandSearch(Model model, @RequestParam String brand) {
        model.addAttribute("posts", postService.showBrand(brand));
        return "posts/list";
    }

    /**
     *
     * @return посты по кузову авто
     */
    @PostMapping("/bodyworkSearch")
    public String bodyworkSearch(Model model, @RequestParam String bodywork) {
        model.addAttribute("posts", postService.showBodywork(bodywork));
        return "posts/list";
    }

    /**
     *
     * @return посты по двигателю авто
     */
    @PostMapping("/engineSearch")
    public String engineSearch(Model model, @RequestParam String engine) {
        model.addAttribute("posts", postService.showEngine(engine));
        return "posts/list";
    }

    /**
     *
     * @return посты пользователя
     */
    @GetMapping("/postUserSearch")
    public String postUserSearch(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("posts", postService.showUserPost(user.getId()));
        return "posts/list";
    }

    /**
     *
     * @return подписки пользователя
     */
    @GetMapping("/subscribe")
    public String subscribe(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        user = userService.findById(user.getId()).get();
        model.addAttribute("posts", postService.showSubscribe(user.getParticipates()));
        return "posts/list";
    }
}
