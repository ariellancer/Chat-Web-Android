package com.example.exe3.infoToDB;

public class ReturnMessage {
    ContactInfo sender;
    String content;

    public ContactInfo getSender() {
        return sender;
    }

    public void setSender(ContactInfo sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReturnMessage(ContactInfo sender, String content) {
        this.sender = sender;
        this.content = content;
    }
}
