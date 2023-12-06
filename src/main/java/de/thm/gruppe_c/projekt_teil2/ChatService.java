package de.thm.gruppe_c.projekt_teil2;

// Imports
import de.thm.oop.chat.base.server.BasicTHMChatServer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Controller class containing the main methode and program logic.
 *
 * @author Gruppe 3
 */
@Service
public class ChatService {

    /**
     * constant reference to the chat server
     */
    private static final BasicTHMChatServer SERVER = new BasicTHMChatServer();

    /**
     * Login and verify the user.
     */
    public boolean login(String username, String password) {

        User.setPassword(password);
        User.setUsername(username);
        try {
            SERVER.getUsers(User.getUsername(), User.getPassword());
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

    /**
     * Display all messages.
     */
    public Message[] getAllMessages(int chatId) {
        Chat chat = User.getChatsById(chatId);

        try {
            //Messages contains every message in raw form
            String[] response = SERVER.getMessages(User.getUsername(), User.getPassword(), 0);

            // Edgecase - User did not receive any messages yet.
            if (response.length == 0) {
                return new Message[0];
            }

            Message[] messages = new Message[response.length];
            for (int i = 0; i < response.length; i++) {
                messages[i] = new Message(response[i]);
            }
            if (chat != null)
                return Arrays.stream(messages).filter(it -> it.getChat().getName().equals(chat.getName())).toArray(Message[]::new);
            else
                return messages;

        } catch (Exception e) {
            return new Message[0];
        }
    }

    public ArrayList<Chat> getAllChats() {
        if (User.getCHATS().isEmpty()) {
            String[] response = new String[0];
            try {
                response = SERVER.getUsers(User.getUsername(), User.getPassword());
            } catch (IOException ignored) {}
            Arrays.stream(response).map(Chat::new).forEach(User::addChat);
        }
        return User.getCHATS();
    }

    public void sendMessage(Message message) {
        Chat c = User.getChatsById(message.getChat().getId());
        assert c != null;
        for (String r : c.getReceivers()) {
            try {
                SERVER.sendTextMessage(
                        User.getUsername(),
                        User.getPassword(),
                        r,
                        message.getContent()
                );
            } catch (IOException ignored) {}
        }
    }
}