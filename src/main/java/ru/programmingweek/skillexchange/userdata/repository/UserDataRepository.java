package ru.programmingweek.skillexchange.userdata.repository;

import com.sun.istack.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;

import java.util.Optional;

@Repository
public interface UserDataRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findById(@NotNull Long id);

    UserEntity findByName(String name);

    UserEntity findByLogin(String login);

    UserEntity findByLoginAndPassword(String login, String password);

}

