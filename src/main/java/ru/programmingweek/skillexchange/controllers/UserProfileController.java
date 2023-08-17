package ru.programmingweek.skillexchange.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.programmingweek.skillexchange.userdata.model.Skills;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;
import ru.programmingweek.skillexchange.userdata.service.SkillsService;
import ru.programmingweek.skillexchange.userdata.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserService userService;
    private final SkillsService skillsService;

    public UserProfileController(UserService userService, SkillsService skillsService) {
        this.userService = userService;
        this.skillsService = skillsService;
    }

    @GetMapping("/{userId}")
    public String showUserProfile(@PathVariable Long userId, Model model) {
        UserEntity user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "userProfile";
    }

    @GetMapping("/{userId}/edit")
    public String editProfile(@PathVariable Long userId, Model model) {
        UserEntity user = userService.getUserById(userId);

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
        UserEntity user = userService.getUserById(userId);
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

        userService.saveUser(user); // Сохранение изменений
        return "redirect:/profile/" + userId;
    }

    @GetMapping("/{userId}/logout")
    public String logout(@PathVariable Long userId, HttpSession session) {
        // Удаление данных аутентификации из сессии
        session.removeAttribute("loggedInUser");
        return "redirect:/";
    }
}

