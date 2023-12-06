package de.thm.gruppe_c.projekt_teil2;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Represents the logged-in user and stores the programm data
 *
 * @author Gruppe 3
 */
public class User {

    @Setter
    @Getter
    private static String username = "";

    @Setter
    @Getter
    private static String password = "";

    @Getter
    private static final ArrayList<Chat> CHATS = new ArrayList<>();

    public static Chat getChatsById(int id) {
        for (Chat chat : CHATS)
        {
            if (chat.getId() == id) {
                return chat;
            }
        }

        return null;
    }



    public static void addChat(Chat chat) {
        User.CHATS.add(chat);
    }

}
