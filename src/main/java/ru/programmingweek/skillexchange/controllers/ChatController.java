package ru.programmingweek.skillexchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.programmingweek.skillexchange.chat.ChatMessagesService;
import ru.programmingweek.skillexchange.chat.Message;
import ru.programmingweek.skillexchange.userdata.model.UserEntity;
import ru.programmingweek.skillexchange.userdata.service.UserDataService;

import javax.servlet.http.HttpSession;
import java.util.List;


//TODO: доделать скрипт js для чата (отправлять сообщения неск пользователям)
@Controller
@RequestMapping("/chat")
public class ChatController {


    private final UserDataService userDataService;
    private final ChatMessagesService chatMessagesService;

    @Autowired
    public ChatController(UserDataService userDataService, ChatMessagesService chatMessagesService) {
        this.userDataService = userDataService;
        this.chatMessagesService = chatMessagesService;
    }

    @GetMapping("/{senderId}/{receiverId}")
    public String viewChat(@PathVariable Long senderId,
                           @PathVariable Long receiverId,
                           Model model,
                           HttpSession session) {
        UserEntity loggedInUser = (UserEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", loggedInUser);
        UserEntity sender = userDataService.getUserById(senderId);
        UserEntity receiver = userDataService.getUserById(receiverId);

        List<Message> messages = chatMessagesService.getMessagesBySenderAndReceiver(sender.getId(), receiver.getId());

        model.addAttribute("sender", sender);
        model.addAttribute("receiver", receiver);
        model.addAttribute("messages", messages);

        return "chat";
    }

    @PostMapping("/send-message")
    public String sendMessage(@RequestParam Long senderId,
                              @RequestParam Long receiverId,
                              @RequestParam String content,
                              Model model) {
        UserEntity sender = userDataService.getUserById(senderId);
        UserEntity receiver = userDataService.getUserById(receiverId);

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);

        chatMessagesService.save(message);

        return "redirect:/chat/" + senderId + "/" + receiverId;
    }
}
