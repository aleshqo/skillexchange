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

import javax.servlet.http.HttpSession;
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
        return "homePageForm";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("loggedInUser", new UserEntity());
        return "registerForm";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loggedInUser", new UserEntity());
        return "loginForm";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute("user") UserEntity user, HttpSession session) {
        if (userService.getUserByLogin(user.getLogin()).isPresent()) {
            ModelAndView errorModel = new ModelAndView("register_form");
            errorModel.addObject("errorMessage", "Логин уже зарегистрирован");
            return errorModel;
        }

        userService.saveUser(user);

        // Сохранение аутентифицированного пользователя в сессии
        session.setAttribute("loggedInUser", user);

        // Перенаправление на страницу профиля
        return new ModelAndView("redirect:/profile/" + user.getId());
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@ModelAttribute("user") UserEntity user, HttpSession session) {
        Optional<UserEntity> existingUser = userService.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        if (existingUser.isEmpty()) {
            ModelAndView errorModel = new ModelAndView("login_form");
            errorModel.addObject("error", "Неверные логин или пароль");
            return errorModel;
        }

        // Сохранение аутентифицированного пользователя в сессии
        session.setAttribute("loggedInUser", existingUser.get());

        // Перенаправление на страницу профиля
        return new ModelAndView("redirect:/profile/" + existingUser.get().getId());
    }
}

