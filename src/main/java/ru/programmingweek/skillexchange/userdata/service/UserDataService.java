package ru.programmingweek.skillexchange.userdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.programmingweek.skillexchange.userdata.model.Skills;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;
import ru.programmingweek.skillexchange.userdata.repository.UserDataRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserDataService {
    //TODO: Временное решение тк проблема с коннектами в БД
    private static final List<UserEntity> users = new CopyOnWriteArrayList<>();
    private static final AtomicLong ID = new AtomicLong(0);
    private final UserDataRepository userRepository;

    @Autowired
    public UserDataService(UserDataRepository userRepository) {
        this.userRepository = userRepository;
        users.add(new UserEntity()
                .setId(777L)
                .setName("Алексей")
                .setAge(30)
                .setSkill(new Skills(1L, "ENGLISH"))
                .setInterest(new Skills(2L, "FRENCH"))
                .setGender("Мужской")
                .setLogin("aleshqo")
                .setPassword("123"));
        users.add(new UserEntity()
                .setId(666L)
                .setName("Димка")
                .setAge(25)
                .setSkill(new Skills(2L, "FRENCH") )
                .setInterest(new Skills(1L, "ENGLISH"))
                .setGender("Мужской")
                .setLogin("dimon")
                .setPassword("123"));
        users.add(new UserEntity()
                .setId(666L)
                .setName("Михаил")
                .setAge(25)
                .setSkill(new Skills(2L, "FRENCH") )
                .setInterest(new Skills(1L, "ENGLISH"))
                .setGender("Мужской")
                .setLogin("mishOK")
                .setPassword("123"));
    }

    @SuppressWarnings("UnusedReturnValue")
    public UserEntity saveUser(UserEntity user) {

//        return userRepository.save(user);
        users.stream()
                .filter(u -> u.getId().equals(user.getId()))
                .findFirst()
                .ifPresentOrElse(
                        existingUser -> users.set(users.indexOf(existingUser), user),
                        () -> {
                            Skills emptySkill = new Skills(0L,"Не указан");
                            user.setId(ID.incrementAndGet());
                            user.setSkill(emptySkill);
                            user.setInterest(emptySkill);
                            users.add(user);
                        }
                );
        System.err.println(users);
        return user;
    }

    public UserEntity getUserById(Long id) {
//        return Optional.of(userRepository.findByLogin(login));
        return users.stream()
                .filter(user -> id.equals(user.getId()))
                .findFirst().orElse(new UserEntity());
    }

    public Optional<UserEntity> getUserByLogin(String login) {
//        return Optional.of(userRepository.findByLogin(login));
        return users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst();
    }

    public Optional<UserEntity> getUserByLoginAndPassword(String login, String password) {
//        return Optional.of(userRepository.findByLoginAndPassword(login, password));
        return users.stream()
                .filter(user -> login.equals(user.getLogin()) && password.equals(user.getPassword()))
                .findFirst();
    }

    public List<UserEntity> getAllUsers() {
//        return (List<UserEntity>) userRepository.findAll();
        return users;
    }

    public void deleteUser(UserEntity user) {
        userRepository.delete(user);
    }
}

