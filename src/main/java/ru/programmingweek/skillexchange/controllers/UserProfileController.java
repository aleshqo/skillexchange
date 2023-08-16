package ru.programmingweek.skillexchange.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.programmingweek.skillexchange.userdata.model.Skills;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;
import ru.programmingweek.skillexchange.userdata.service.SkillsService;
import ru.programmingweek.skillexchange.userdata.service.UserService;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserService userService; // Ваш сервис для работы с пользователями
    private final SkillsService skillsService; // Ваш сервис для работы с навыками

    public UserProfileController(UserService userService, SkillsService skillsService) {
        this.userService = userService;
        this.skillsService = skillsService;
    }

    @GetMapping("/{userId}")
    public String viewProfile(@PathVariable Long userId, Model model) {
        UserEntity user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "userProfile";
    }

    @GetMapping("/{userId}/edit")
    public String editProfile(@PathVariable Long userId, Model model) {
        UserEntity user = userService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("skills", skillsService.getAllSkills()); // Получение списка навыков
        return "editProfile";
    }

    @PostMapping("/{userId}/save")
    public String saveProfile(@PathVariable Long userId,
                              @RequestParam String name,
                              @RequestParam int age,
                              @RequestParam String gender,
                              @RequestParam Long skillId,
                              // Дополнительные параметры профиля
                              Model model) {
        UserEntity user = userService.getUserById(userId);
        Skills skill = skillsService.getSkillById(skillId);
        // Обновление данных профиля
        user.setName(name);
        user.setAge(age);
        user.setGender(gender);
        user.setSkill(skill);
        // Обновление других данных профиля

        userService.saveUser(user); // Сохранение изменений
        return "redirect:/profile/" + userId;
    }
}

