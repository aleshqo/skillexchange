package ru.programmingweek.skillexchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.programmingweek.skillexchange.userdata.model.Skills;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;
import ru.programmingweek.skillexchange.userdata.service.UserDataService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPairingService {

    private final UserDataService userService;

    @Autowired
    public UserPairingService(UserDataService userService) {
        this.userService = userService;
    }

    public List<UserEntity> findMatchingPair(UserEntity user) {
        return userService.getAllUsers().stream()
                .filter(otherUser -> !otherUser.equals(user))
                .filter(otherUser -> hasCommonSkillOrInterest(user, otherUser))
                .collect(Collectors.toList());
    }

    private boolean hasCommonSkillOrInterest(UserEntity user1, UserEntity user2) {
        // TODO: Пока самый тупой алгоритм
        Skills skills1 = user1.getSkill();
        Skills interests1 = user1.getInterest();
        Skills skills2 = user2.getSkill();
        Skills interests2 = user2.getInterest();

        return skills1.equals(interests2) && interests1.equals(skills2);
    }
}


