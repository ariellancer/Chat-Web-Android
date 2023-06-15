package com.example.exe3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.ContactInfo;
import com.example.exe3.infoToDB.LastMessage;
import com.example.exe3.webService.ChatApi;
import com.example.exe3.webService.UserApi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContactRepository {
    private UserApi userApi;
    private ChatApi chatApi;
    private ContactListData contactListData;


    public ContactRepository() {
        contactListData = new ContactListData();
    }


    class ContactListData extends MutableLiveData<List<Contact>> {
        final private int[] profilePictures = {
                R.drawable.favicon, R.drawable.gray2, R.drawable.modiin,
                R.drawable.omer, R.drawable.peakpx,
                R.drawable.favicon, R.drawable.gray2, R.drawable.modiin
        };
        final private String[] userNames = {
                "Toni Kroos", "Manuel Neuer", "Sergio Ramos", "Luka Modrić ", "Thomas Müller",
                "Kylian Mbappe", "Neymar Jr", "Eran Levy",
        };

        final private String[] lastMassages = {
                "Hi, how are you?", "24K Magic", "Missing Madrid :(", "Wanna hear a joke?", "Yo!",
                "Well....", "Did you see the latest John Wick?",
                "I'm the best!"
        };

        final private String[] times = {
                "12:00", "00:30", "03:23", "08:59", "12:23", "22:54", "11:47", "10:04",
        };
        List<Contact> contacts = new LinkedList<>();
        public ContactListData() {
            super();

            for (int i = 0; i < profilePictures.length; i++) {
                ContactInfo info = new ContactInfo(userNames[i],userNames[i],"a");
                LastMessage lastMessage = new LastMessage(i,times[i],lastMassages[i]);
                Contact aUser = new Contact(i,info,lastMessage);

                contacts.add(aUser);
            }
            setValue(contacts);
        }
    }
    public LiveData<List<Contact>> getAll(){return contactListData;}

    public void addContact(final Contact contact){

    }
    public void deleteContact(final Contact contact) {
    }
    public void reloadContact(){

    }
    }