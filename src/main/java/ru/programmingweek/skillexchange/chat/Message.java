package ru.programmingweek.skillexchange.chat;

import lombok.Data;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;

import javax.persistence.*;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity sender;

    @ManyToOne
    private UserEntity receiver;

    private String content;
}

