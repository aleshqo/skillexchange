package ru.programmingweek.skillexchange.userdata.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String gender;
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skills skill;
    @ManyToOne
    @JoinColumn(name = "interest_id")
    private Skills interest;
    private String login;
    private String password;
}



