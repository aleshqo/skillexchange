package ru.programmingweek.skillexchange.chat;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

//TODO: Здесь по идее должна быть работа с БД
@Service
public class ChatMessagesService {

    private static final List<Message> MESSAGE_LIST = new CopyOnWriteArrayList<>();
    private static final AtomicLong ID = new AtomicLong();

    public void save(Message message) {
        message.setId(ID.incrementAndGet());
        MESSAGE_LIST.add(message);
    }

    public List<Message> getMessagesBySenderAndReceiver(Long senderId, Long receiverId) {
        return MESSAGE_LIST.stream()
                .filter(message ->
                        senderId.equals(message.getSender().getId()) && receiverId.equals(message.getReceiver().getId()))
                .sorted(Comparator.comparing(Message::getId))
                .collect(Collectors.toList());
    }
}
