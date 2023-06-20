package com.example.exe3.infoToDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey
    private String id;
    private String created;
    private ContactInfo sender;

    private String content;

    public Message(String  id, String created, ContactInfo sender, String content) {
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;

    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public ContactInfo getSender() {
        return sender;
    }

    public void setSender(ContactInfo sender) {
        this.sender = sender;
    }

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static class Sender{
        private String username;

        public Sender(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

}
