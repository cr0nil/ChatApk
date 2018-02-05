package com.example.karol.chatapk;

import java.util.Date;

/**
 * Created by Karol on 01.02.2018.
 */

public class ChatMessege {
    private String messageText;
    private String messageUser;
    private long messageTime;

    public ChatMessege(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        messageTime = new Date().getTime();
    }

    public ChatMessege() {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
