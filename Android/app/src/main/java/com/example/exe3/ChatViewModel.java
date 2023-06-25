package com.example.exe3;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.NewMessage;

public class ChatViewModel {


    private static ChatViewModel instance;
    private LiveData<Chat> messages;
    private ContactRepository repository;
    private String token;

    private ChatViewModel(Context applicationContext,String token) {
        repository = new ContactRepository(applicationContext);
        messages = repository.getAllLiveMessages();
        this.token=token;
    }


    public static ChatViewModel getInstance(Context applicationContext,String token) {
        if (instance == null) {
            instance = new ChatViewModel(applicationContext,token);
        }
        return instance;
    }

    public LiveData<Chat> getLiveMessages() {
        return messages;
    }

    public void getMessages( int id) {
        repository.getMessages(token, id);
    }

    public void postMessagesById( int id, NewMessage newMessage) {
        repository.postMessagesById(token, id, newMessage);
    }
    public static ChatViewModel getInstance() {
        return instance;
    }
}

