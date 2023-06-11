package com.example.exe3;

public class Message {
    private String contentOfMessage;
    private String timeOfMessage;
    private String sender;
    public Message(String contentOfMessage,String timeOfMessage,String sender){
        this.contentOfMessage=contentOfMessage;
        this.timeOfMessage=timeOfMessage;
        this.sender = sender;
    }
    public String getContentOfMessage(){
        return contentOfMessage;
    }
    public String getTimeOfMessage(){
        return timeOfMessage;
    }
    public String getSender(){
        return sender;
    }
}
