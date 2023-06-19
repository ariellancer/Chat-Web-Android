package com.example.exe3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.Message;
import com.example.exe3.infoToDB.NewMessage;

import java.util.ArrayList;
import java.util.List;

public class ContactViewModel extends ViewModel {
    ContactRepository repository;
    private LiveData<List<Contact>> contacts;
    private LiveData<Chat> messages;


    public ContactViewModel(){
        repository=new ContactRepository();
        contacts =repository.getAll();
        messages=repository.getAllLiveMessages();
    }

    public LiveData<List<Contact>> get(){return contacts;}

    public LiveData<Chat> getLiveMessages(){return messages;}
    public void addContact(Contact contact){repository.addContact(contact);}
    public void deleteContact(Contact contact){repository.deleteContact(contact);}
    public void getMessages(String token,int id){repository.getMessages(token,id);}
    public void postMessagesById(String token, int id, NewMessage newMessage){repository.postMessagesById(token,id,newMessage);}
    public void getContacts (String token){repository.getContacts(token);}

}
