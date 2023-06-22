package com.example.exe3;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.NewMessage;

import java.util.List;

public class ContactViewModel extends ViewModel {
    ContactRepository repository;
    private LiveData<List<Contact>> contacts;
    private LiveData<Chat> messages;


    public ContactViewModel(Context applicationContext){
        repository=new ContactRepository(applicationContext);
        contacts =repository.getAll();
        messages=repository.getAllLiveMessages();
    }

    public LiveData<List<Contact>> get(){return contacts;}

    public LiveData<Chat> getLiveMessages(){return messages;}
    public void addContact(Context activity,String username, String token){  repository.addContact(activity,username,token);}
    public void deleteContact(String token,int id){repository.deleteContact(token,id);}
    public void getMessages(String token,int id){repository.getMessages(token,id);}
    public void postMessagesById(String token, int id, NewMessage newMessage){repository.postMessagesById(token,id,newMessage);}
    public void getContacts (String token){repository.getContacts(token);}

}
