package com.example.exe3.infoToDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Contact {
    private int id;

    private ContactInfo user;
    private LastMessage lastMessage;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContactInfo getUser() {
        return user;
    }

    public void setUser(ContactInfo user) {
        this.user = user;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Contact(int id, ContactInfo user, LastMessage lastMessage) {
        this.id = id;
       this.user=user;
        this.lastMessage = lastMessage;
    }
}