package ru.job4j.cars.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/register")
    public String getRegistationPage() {
        return "users/register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user) {
        var savedUser = userService.save(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("error", "Пользователь с такой почтой уже существует");
            return "users/register";
        }
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Почта или пароль введены неверно");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/posts";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

    @GetMapping("/subscribe/{id}")
    public String subscribe(Model model, @PathVariable int id, HttpSession session) {
        var user = (User) session.getAttribute("user");
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            return "errors/404";
        }
        var post = postOptional.get();
        user = userService.findById(user.getId()).get();
        boolean added = user.getParticipates().add(post);
        if (!added) {
            model.addAttribute("message",
                    String.format("Вы уже подписаны на объявление %s владельца %s",
                            post.getCar().getName(),
                            post.getCar().getOwner().getName()));
            return "errors/409";
        }
        userService.update(user);
        model.addAttribute("message",
                String.format("Вы успешно подписались на объявление %s владельца %s",
                        post.getCar().getName(),
                        post.getCar().getOwner().getName()));
        return "request/200";
    }
}
