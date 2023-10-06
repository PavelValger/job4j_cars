package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostCreating;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.PostService;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        return "posts/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute PostCreating postCreating,
                         HttpSession session, @RequestParam MultipartFile file, Model model) {
        var user = (User) session.getAttribute("user");
        try {
            postService.save(postCreating, user,
                    new FileDto(file.getOriginalFilename(), file.getBytes()));
            return "redirect:/posts";
        } catch (IOException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }
}
