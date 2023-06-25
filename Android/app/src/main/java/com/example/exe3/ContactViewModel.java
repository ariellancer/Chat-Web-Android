package com.example.exe3;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.NewMessage;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private static ContactViewModel instance;

    private LiveData<List<Contact>> contacts;
    private ContactRepository repository;
    private String token;
    private ContactViewModel(Context applicationContext,String token) {
        repository = new ContactRepository(applicationContext);
        contacts = repository.getAll();
        this.token=token;
    }

    public static ContactViewModel getInstance() {
        return instance;
    }

    public static ContactViewModel getInstance(Context applicationContext,String token ) {
        if (instance == null) {
            instance = new ContactViewModel(applicationContext,token);
        }
        return instance;
    }

    public LiveData<List<Contact>> getLiveContacts() {
        return contacts;
    }

    public void addContact(Context activity, String username) {
        repository.addContact(activity, username, token);
    }

    public void deleteContact( int id) {
        repository.deleteContact(token, id);
    }

    public void getContacts() {
        repository.getContacts(token);
    }
}

