package de.thm.gruppe_c.projekt_teil2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping("")
    public String app(
            @RequestParam(name = "chat", required = false) Integer current_chat,
            Model model
    ) {
        if (User.getPassword().isEmpty()) {
            return "redirect:/login";
        }

        if (current_chat == null) current_chat = 0;
        ArrayList<Chat> chats = chatService.getAllChats();
        Message newMessage = new Message();
        for (Chat chat : chats) {
            if (chat.getId() == current_chat) {
                newMessage.setChat(chat);
                break;
            }
        }
        model.addAttribute("messages", chatService.getAllMessages(current_chat));
        model.addAttribute("chats", chats);
        model.addAttribute("new_message", newMessage);
        return "index";
    }

    @PostMapping("")
    public String sendMessage(
            @ModelAttribute("new_message") Message message,
            Model model
    ) {
        chatService.sendMessage(message);
        return app(message.getChat().getId(), model);
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("credentials", new Credentials());
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("credentials") Credentials credentials) {
        System.out.println("test");
        if (!chatService.login(credentials.getUsername(), credentials.getPassword())) {
            return "login";
        }
        return "redirect:/";
    }
}
