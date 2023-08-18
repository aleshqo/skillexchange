package ru.programmingweek.skillexchange.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.programmingweek.skillexchange.services.UserPairingService;
import ru.programmingweek.skillexchange.userdata.model.Skills;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;
import ru.programmingweek.skillexchange.userdata.service.SkillsService;
import ru.programmingweek.skillexchange.userdata.service.UserDataService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserDataService userDataService;
    private final SkillsService skillsService;
    private final UserPairingService userPairingService;


    public UserProfileController(UserDataService userDataService, SkillsService skillsService, UserPairingService userPairingService) {
        this.userDataService = userDataService;
        this.skillsService = skillsService;
        this.userPairingService = userPairingService;
    }

    @GetMapping("/{userId}")
    public String showUserProfile(@PathVariable Long userId, HttpSession session, Model model) {
        UserEntity user = userDataService.getUserById(userId);
        model.addAttribute("user", user);

        UserEntity loggedInUser = (UserEntity) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);

        return "userProfile";
    }

    @GetMapping("/{userId}/edit")
    public String editProfile(@PathVariable Long userId, Model model) {
        UserEntity user = userDataService.getUserById(userId);

        List<Skills> skillsList = skillsService.getAllSkills();

        model.addAttribute("user", user);
        model.addAttribute("skillsList", skillsList);
        return "editProfile";
    }

    @PostMapping("/{userId}/save")
    public String saveProfile(@PathVariable Long userId,
                              @RequestParam String name,
                              @RequestParam int age,
                              @RequestParam String gender,
                              @RequestParam Long skillId,
                              @RequestParam Long interestId,
                              // Дополнительные параметры профиля
                              Model model) {
        UserEntity user = userDataService.getUserById(userId);
        Optional<Skills> skill = skillsService.getSkillById(skillId);
        Optional<Skills> interest = skillsService.getSkillById(interestId);
        // Обновление данных профиля
        user
                .setName(name)
                .setAge(age)
                .setGender(gender)
                .setSkill(skill.orElse(null))
                .setInterest(interest.orElse(null));
        // Обновление других данных профиля

        userDataService.saveUser(user); // Сохранение изменений
        return "redirect:/profile/" + userId;
    }

    @GetMapping("/{userId}/recommendations")
    public String showRecommendations(@PathVariable Long userId, Model model, HttpSession httpSession) {
        if (isLoggedIn(httpSession)) {
            UserEntity user = userDataService.getUserById(userId);

            List<UserEntity> matchingPairs = userPairingService.findMatchingPair(user);

            model.addAttribute("user", user);
            model.addAttribute("matchingPair", matchingPairs);

            return "recommendations";
        }
        return "redirect:/login";
    }

    @GetMapping("/{userId}/logout")
    public String logout(@PathVariable Long userId, HttpSession session) {
        // Удаление данных аутентификации из сессии
        session.removeAttribute("loggedInUser");
        return "redirect:/";
    }

    private boolean isCurrentUser(Long userId, HttpSession session) {
        UserEntity loggedInUser = (UserEntity) session.getAttribute("loggedInUser");
        return loggedInUser != null && loggedInUser.getId().equals(userId);
    }

    private boolean isLoggedIn(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("loggedInUser");
        return user != null;
    }
}

