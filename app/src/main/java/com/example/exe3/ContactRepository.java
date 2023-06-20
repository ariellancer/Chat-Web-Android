package com.example.exe3;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.NewMessage;
import com.example.exe3.infoToDB.ReturnMessage;
import com.example.exe3.webService.ChatApi;
import com.example.exe3.webService.UserApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ContactRepository {
    private UserApi userApi;
    private ChatApi chatApi;
    private ContactListData contactListData;

    private MessagesListData messagesListData;


    public ContactRepository() {
        contactListData = new ContactListData();
        chatApi = ChatApi.getInstance();
        messagesListData = new MessagesListData();
    }

    static class MessagesListData extends MutableLiveData<Chat> {
        Chat messages;

        public MessagesListData() {
            super();
            messages = new Chat();
        }
    }

    static class ContactListData extends MutableLiveData<List<Contact>> {
        ArrayList<Contact> contacts;

        public ContactListData() {
            super();
            contacts = new ArrayList<>();
        }

    }

    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public LiveData<Chat> getAllLiveMessages() {
        return messagesListData;
    }




    public void getMessages(String token, int id) {
        CompletableFuture<Chat> future = chatApi.getMessages("bearer " + token, id)
                .thenApply(messages -> messages).exceptionally(error -> {
                    //Toast(error.getMessage())
                    return null;
                });
        future.thenAccept(messages -> {
            if (messages != null) {
                messagesListData.setValue(messages);
//                messagesListData.messages.clear();
//                messagesListData.messages.addAll(messages);
            }
        });
    }
    public void postMessagesById(String token, int id, NewMessage newMessage) {
        CompletableFuture<ReturnMessage> future = chatApi.postMessagesById("bearer " + token, id,newMessage)
                .thenApply(message -> message).exceptionally(error -> {
                    //Toast(error.getMessage())
                    return null;
                });
        future.thenAccept(message -> {
            if (message != null) {
                getMessages(token,id);
            }
        });
    }
    public void getContacts(String token) {
        CompletableFuture<ArrayList<Contact>> future = chatApi.getContact("bearer " + token).
                thenApply(contacts -> contacts).exceptionally(error -> {
                    //Toast(error.getMessage())
                    return null;
                });
        future.thenAccept(contacts -> {
            if (contacts != null) {
                contactListData.setValue(contacts);
//                contactListData.contacts.clear();
//                contactListData.contacts.addAll(contacts);
            }
        });

    }

    public void addContact(Context activity,String username, String token){
        CompletableFuture<Contact> future = chatApi.addContact(activity,username,"bearer " + token)
                .thenApply(response -> response)
                .exceptionally(error -> {
                    //Toast(error.getMessage())
                    return null;
                });
        future.thenAccept(response -> {
            if(response!=null){
                getContacts(token);
            }

        });

    }
    public void deleteContact(String token, int id) {
        chatApi.deleteContact(id, "bearer " + token)
                .thenAccept(response -> {
                    getContacts(token);
                })
                .exceptionally(error -> {
                    // Handle exception if needed
                    return null;
                });
    }
}