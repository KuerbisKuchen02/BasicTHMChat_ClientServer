package de.thm.gruppe_c.projekt_teil2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chat {

    private static int COUNT = 0;

    private int id;

    /**
     * Name of the Chat
     */
    private String name;

    /**
     * array with all receivers
     */
    private String[] receivers;

    public Chat() {
    }

    public Chat(String username) {
        this.id = COUNT++;
        this.name = username;
        receivers = new String[] {username};
    }

    /**
     * @param name      name of the broadcast
     * @param usernames String array with all usernames
     */
    public Chat(String name, String[] usernames) {
        this.id = COUNT++;
        this.name = name;
        receivers = new String[usernames.length];
        System.arraycopy(usernames, 0, receivers, 0, usernames.length);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(name + " [");

        // Add all receivers in [] Brackets
        for (String receiver : receivers) {
            ret.append(receiver).append(", ");
        }
        ret = new StringBuilder(ret.substring(0, ret.length() - 2));
        ret.append("]");

        return ret.toString();
    }
}
