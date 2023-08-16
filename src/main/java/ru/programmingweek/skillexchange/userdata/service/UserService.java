package ru.programmingweek.skillexchange.userdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;
import ru.programmingweek.skillexchange.userdata.repository.UserDataRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    //TODO: Временное решение тк проблема с коннектами в БД
    private static final List<UserEntity> users = new CopyOnWriteArrayList<>();
    private static final AtomicLong ID = new AtomicLong(0);
    private final UserDataRepository userRepository;

    @Autowired
    public UserService(UserDataRepository userRepository) {
        this.userRepository = userRepository;
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
                            user.setId(ID.incrementAndGet());
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
        return (List<UserEntity>) userRepository.findAll();
    }

    public void deleteUser(UserEntity user) {
        userRepository.delete(user);
    }
}

