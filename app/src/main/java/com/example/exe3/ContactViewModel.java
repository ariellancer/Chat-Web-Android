package com.example.exe3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.exe3.infoToDB.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactViewModel extends ViewModel {
    ContactRepository repository;
    private LiveData<List<Contact>> contacts;


    public ContactViewModel(){
        repository=new ContactRepository();
        contacts =repository.getAll();
    }

    public LiveData<List<Contact>> get(){return contacts;}
    public void addContact(Contact contact){repository.addContact(contact);}
    public void deleteContact(Contact contact){repository.deleteContact(contact);}
    public void reloadContact(){repository.reloadContact();}

}
