package com.example.exe3.infoToDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;
@Entity
public class Chat {
    @PrimaryKey(autoGenerate=true)
    private int id;
    @TypeConverters(ContactInfoListConverter.class)
    private List<ContactInfo>  users;
    @TypeConverters(MessageListConverter.class)
    private List<Message> messages;

    public Chat(int id, List<ContactInfo> users, List<Message> messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ContactInfo> getUsers() {
        return users;
    }

    public void setUsers(List<ContactInfo> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
