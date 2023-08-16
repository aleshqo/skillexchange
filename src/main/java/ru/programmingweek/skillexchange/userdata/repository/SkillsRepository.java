package ru.programmingweek.skillexchange.userdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.programmingweek.skillexchange.userdata.model.Skills;

public interface SkillsRepository extends CrudRepository<Skills, Long> {
}

