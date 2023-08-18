package ru.programmingweek.skillexchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.programmingweek.skillexchange.userdata.model.Skills;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;
import ru.programmingweek.skillexchange.userdata.service.UserDataService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserPairingService {

    private final UserDataService userService;

    @Autowired
    public UserPairingService(UserDataService userService) {
        this.userService = userService;
    }

    public List<UserEntity> findMatchingPair(UserEntity user) {
        List<UserEntity> matchingPairs = new ArrayList<>();

        List<UserEntity> allUsers = userService.getAllUsers();

        for (UserEntity otherUser : allUsers) {
            if (!otherUser.equals(user)) {
                if (hasCommonSkillOrInterest(user, otherUser)) {
                    matchingPairs.add(otherUser);
                }
            }
        }

        return matchingPairs;
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


