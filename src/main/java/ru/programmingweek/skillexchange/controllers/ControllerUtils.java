package ru.programmingweek.skillexchange.controllers;

import lombok.experimental.UtilityClass;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;

import javax.servlet.http.HttpSession;

@UtilityClass
public class ControllerUtils {

    public static final String LOGGED_IN_USER_ATTR_NAME = "loggedInUser";
    public static final String BROWSE_USER_ATTR_NAME = "user";
    public static final String REDIRECT_TO_PROFILE = "redirect:/profile/";
    public static final String REDIRECT_TO_LOGIN = "redirect:/login";


    public static UserEntity getLoggedInUserFromSession(HttpSession session) {
        return (UserEntity) session.getAttribute(LOGGED_IN_USER_ATTR_NAME);
    }

    public static boolean isLoggedIn(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("loggedInUser");
        return user != null;
    }
}
