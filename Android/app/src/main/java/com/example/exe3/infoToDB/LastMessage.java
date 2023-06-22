package com.example.exe3.infoToDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LastMessage{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String created;
    private String content;

    public LastMessage(int id, String created, String content) {
        this.id = id;
        this.created = created;
        this.content = content;
    }
}
