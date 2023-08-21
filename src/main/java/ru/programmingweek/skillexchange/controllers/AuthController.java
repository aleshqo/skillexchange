package ru.programmingweek.skillexchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;
import ru.programmingweek.skillexchange.userdata.service.UserDataService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static ru.programmingweek.skillexchange.controllers.ControllerUtils.*;

//TODO: Переделать на spring-security
@Controller
public class AuthController {

    private static final String LOGIN_PATH = "/login";
    private static final String REGISTER_PATH = "/register";
    private final UserDataService userDataService;

    @Autowired
    public AuthController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping("/")
    public String showHomePage() {
        return "homePageForm";
    }

    @GetMapping(REGISTER_PATH)
    public String showRegistrationForm(HttpSession session) {
        UserEntity user = getLoggedInUserFromSession(session);
        if (user != null) {
            return REDIRECT_TO_PROFILE + user.getId();
        }
        return "registerForm";
    }

    @GetMapping(LOGIN_PATH)
    public String showLoginForm(HttpSession session) {
        UserEntity user = getLoggedInUserFromSession(session);
        if (user != null) {
            return REDIRECT_TO_PROFILE + user.getId();
        }
        return "loginForm";
    }

    @PostMapping(REGISTER_PATH)
    public ModelAndView registerUser(@ModelAttribute(LOGGED_IN_USER_ATTR_NAME) UserEntity user, HttpSession session) {
        if (userDataService.getUserByLogin(user.getLogin()).isPresent()) {
            ModelAndView errorModel = new ModelAndView("register_form");
            errorModel.addObject("errorMessage", "Логин уже зарегистрирован");
            return errorModel;
        }

        userDataService.saveUser(user);

        // Сохранение аутентифицированного пользователя в сессии
        session.setAttribute(LOGGED_IN_USER_ATTR_NAME, user);

        // Перенаправление на страницу профиля
        return new ModelAndView(REDIRECT_TO_PROFILE + user.getId());
    }

    @PostMapping(LOGIN_PATH)
    public ModelAndView loginUser(@ModelAttribute("user") UserEntity user, HttpSession session) {
        Optional<UserEntity> existingUser = userDataService.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        if (existingUser.isEmpty()) {
            ModelAndView errorModel = new ModelAndView("loginForm");
            errorModel.addObject("error", "Неверные логин или пароль");
            return errorModel;
        }

        // Сохранение аутентифицированного пользователя в сессии
        session.setAttribute(LOGGED_IN_USER_ATTR_NAME, existingUser.get());

        // Перенаправление на страницу профиля
        return new ModelAndView(REDIRECT_TO_PROFILE + existingUser.get().getId());
    }
}

