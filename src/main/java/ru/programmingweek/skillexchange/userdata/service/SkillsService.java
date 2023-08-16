package ru.programmingweek.skillexchange.userdata.service;

import org.springframework.stereotype.Service;
import ru.programmingweek.skillexchange.userdata.model.Skills;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;
import ru.programmingweek.skillexchange.userdata.repository.SkillsRepository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SkillsService {

    //TODO: Временное решение тк проблема с коннектами в БД
    private static final List<Skills> skillsList = new CopyOnWriteArrayList<>();
    private static final AtomicLong ID = new AtomicLong(0);
    private final SkillsRepository skillRepository;

    public SkillsService(SkillsRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skills saveSkill(Skills skill) {
        // return skillRepository.save(skills);
        skillsList.stream()
                .filter(s -> s.getId().equals(skill.getId()))
                .findFirst()
                .ifPresentOrElse(
                        existingSkill -> skillsList.set(skillsList.indexOf(existingSkill), skill),
                        () -> {
                            skill.setId(ID.incrementAndGet());
                            skillsList.add(skill);
                        }
                );
        System.err.println(skillsList);
        return skill;
    }

    public Skills getSkillById(Long id) {
        return skillsList.stream()
                .filter(s -> id.equals(s.getId()))
                .findFirst()
                .orElse(new Skills().setId(1L).setName("English"));
    }

    public List<Skills> getAllSkills() {
//        return (List<Skills>) skillRepository.findAll();
        return skillsList;
    }
}

