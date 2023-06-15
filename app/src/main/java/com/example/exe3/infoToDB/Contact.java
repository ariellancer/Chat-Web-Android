package com.example.exe3.infoToDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate=true)
    private int id;

    private ContactInfo user;
    private LastMessage lastMassage;

    public Contact(int id, ContactInfo user, LastMessage lastMassage) {
        this.id = id;
        this.user = user;
        this.lastMassage = lastMassage;
    }

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

    public LastMessage getLastMassage() {
        return lastMassage;
    }

    public void setLastMassage(LastMessage lastMassage) {
        this.lastMassage = lastMassage;
    }

}