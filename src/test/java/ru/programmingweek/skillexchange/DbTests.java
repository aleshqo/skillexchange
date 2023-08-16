package ru.programmingweek.skillexchange;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.programmingweek.skillexchange.userdata.model.Skills;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;
import ru.programmingweek.skillexchange.userdata.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DbTests {

    @Autowired
    private UserService userService;

    @Test
    void crudTest() {
//        UserEntity user = new UserEntity()
//                .setId(1L)
//                .setName("Aleksey")
//                .setGender("M")
//                .setAge(22)
//                .setSkill(new Skills().setId(1L).setName("ENGLISH"))
//                .setInterest(new Skills().setId(2L).setName("FRENCH"));
//        UserEntity addedUser = userService.createUser(user);
//        UserEntity getUser = userService.getUser(user);
//
//        assertEquals(addedUser, getUser, "Пользователи не равны");
    }
}
