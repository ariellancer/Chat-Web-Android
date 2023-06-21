package com.example.exe3.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class ContactForRoom {
    @PrimaryKey
    @NotNull
    int id;
    String username;
    String displayName;
    String profilePic;
    private int idOfLastMessage;
    private String created;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getIdOfLastMessage() {
        return idOfLastMessage;
    }

    public void setIdOfLastMessage(int idOfLastMessage) {
        this.idOfLastMessage = idOfLastMessage;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContactForRoom(int id, String username, String displayName, String profilePic, int idOfLastMessage, String created, String content) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.idOfLastMessage = idOfLastMessage;
        this.created = created;
        this.content = content;
    }
}