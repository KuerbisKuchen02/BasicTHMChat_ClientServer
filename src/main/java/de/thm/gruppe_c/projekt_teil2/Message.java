package de.thm.gruppe_c.projekt_teil2;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class Message {

    /**
     * Message ID, type int, incrementing
     */
    private final int id;

    /**
     * timestamp in format "YYYY-MM-DD HH:MM:SS"
     */
    private final LocalDateTime timestamp;

    /**
     * direction either "in" or "out"
     */
    private final String direction;

    /**
     * Username of chat
     */
    @Setter
    private Chat chat;

    /**
     * Type either "txt" or "img"
     */
    @Setter
    private String type;

    /**
     * content of the message, in case of an image
     * Local or remote path of the Image
     */
    @Setter
    private String content;

    /**
     * MIME type "image/[type either "png" or "jpg"]"
     */
    private String mimetype;

    /**
     * size of image in Byte
     */
    private int size;

    public Message() {
        id = -1;
        direction = "out";
        timestamp = LocalDateTime.now();
    }

    /**
     * Constructor of class TextMessage
     * This constructor should be used when creating a message which was loaded from the server.
     *
     * @param raw: String with every attribute of a message in it, split with '|'
     */
    public Message(String raw) {
        String[] split = raw.split("\\|");
        id = Integer.parseInt(split[0]);
        timestamp = LocalDateTime.parse(split[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        direction = split[2];
        chat = new Chat(split[3]);
        type = split[4];
        if (type.equals("txt")) {
            if (split.length != 6) throw new IllegalArgumentException();
            content = split[5];
            mimetype = null;
            size = -1;
        } else {
            if (split.length != 8) throw new IllegalArgumentException();
            mimetype = split[5];
            size = Integer.parseInt(split[6]);
            content = split[7];
        }
    }

    public String getStringDate() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm"));
    }

    /**
     * @return string representation from the meta information of the message
     */
    @Override
    public String toString() {
        String ret = "";

        ret += "[" + timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm")) + "] ";

        if (direction.equals("in")) {
            ret += chat + " >> YOU:\n";
        } else {
            ret += "YOU >> " + chat + ":\n";
        }

        if (type.equals("txt")) {
            return ret + content;
        } else {
            return ret + "Path: " + content + " (Type: " + mimetype.substring(6) + ", Size: " + size + " bytes)";
        }
    }
}
