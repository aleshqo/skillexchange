package ru.programmingweek.skillexchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;
import ru.programmingweek.skillexchange.userdata.service.UserService;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHomePage() {
        return "home_page";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register_form";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login_form";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute("user") UserEntity user) {
        if (userService.getUserByLogin(user.getLogin()).isPresent()) {
            ModelAndView errorModel = new ModelAndView("register_form");
            errorModel.addObject("errorMessage", "Логин уже зарегистрирован");
            return errorModel;
        }

        userService.saveUser(user);

        ModelAndView successModel = new ModelAndView("register_form");
        successModel.addObject("message", "Пользователь успешно зарегистрирован");
        return successModel;
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@ModelAttribute("user") UserEntity user) {
        Optional<UserEntity> existingUser = userService.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        if (existingUser.isEmpty()) {
            ModelAndView errorModel = new ModelAndView("login_form");
            errorModel.addObject("error", "Неверные логин или пароль");
            return errorModel;
        }

        ModelAndView successModel = new ModelAndView("login_form");
        successModel.addObject("message", "Вход выполнен успешно");
        return successModel;
    }
}

