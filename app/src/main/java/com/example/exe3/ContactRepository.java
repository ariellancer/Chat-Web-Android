package com.example.exe3;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.exe3.Room.ChatRoomDao;
import com.example.exe3.Room.ContactForRoom;
import com.example.exe3.Room.ContactRoomDao;
import com.example.exe3.Room.RoomAppDB;
import com.example.exe3.activity.Register;
import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.LastMessage;
import com.example.exe3.infoToDB.NewMessage;
import com.example.exe3.infoToDB.ReturnMessage;
import com.example.exe3.webService.ChatApi;
import com.example.exe3.webService.UserApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ContactRepository {
    private UserApi userApi;
    private ChatApi chatApi;
    private ContactListData contactListData;

    private MessagesListData messagesListData;
    private RoomAppDB roomAppDB;
    private ContactRoomDao contactRoomDao;
    private ChatRoomDao chatRoomDao;
    private boolean ifInitialize;
    private boolean exists;

    public ContactRepository(Context applicationContext) {
        ifInitialize=false;
        exists=false;
        contactListData = new ContactListData();
        chatApi = ChatApi.getInstance();
        messagesListData = new MessagesListData();
        roomAppDB=Room.databaseBuilder(applicationContext,RoomAppDB.class
       ,"RoomDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        contactRoomDao= roomAppDB.contactRoomDao();
        chatRoomDao=roomAppDB.chatRoomDao();
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
        Chat chat=chatRoomDao.get(id);
        if(chat!=null){
            exists=true;
            messagesListData.setValue(chat);
        }
        CompletableFuture<Chat> future = chatApi.getMessages("bearer " + token, id)
                .thenApply(messages -> messages).exceptionally(error -> {
                    //Toast(error.getMessage())
                    return null;
                });
        future.thenAccept(messages -> {
            if (messages != null) {
                if (exists) {
                    chatRoomDao.update(messages);
                    exists=false;
                }else {
                    chatRoomDao.insert(messages);
                }
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
        List<ContactForRoom> contactForRooms=contactRoomDao.getContactsList();
        CompletableFuture<ArrayList<Contact>> future = chatApi.getContact("bearer " + token).
                thenApply(contacts -> contacts).exceptionally(error -> {
                    //Toast(error.getMessage())
                    return null;
                });
        future.thenAccept(contacts -> {
            if (contacts != null) {
                for (int i=0;i<contactForRooms.size();i++){
                    contactRoomDao.deleteContact(contactForRooms.get(i));
                }
                for (int i=0;i<contacts.size();i++){
//                    Log.e("123", "getContacts: " );
                    contactRoomDao.insertContact(createContactRoom(contacts.get(i)));
                }
                if(!ifInitialize){
                    initializeChats(contacts,token);
                    ifInitialize=true;
                }
                contactListData.setValue(contacts);

            }
        });

    }

    public void addContact(Context activity,String username, String token){
        List<ContactForRoom> temp=contactRoomDao.getContactsList();
        for(ContactForRoom contact:temp){
            if(contact.getUsername().equals(username)){
                Toast.makeText(activity, "Friend already excises " , Toast.LENGTH_SHORT).show();
                return;
            }
        }

        CompletableFuture<Contact> future = chatApi.addContact(activity,username,"bearer " + token)
                .thenApply(response -> response)
                .exceptionally(error -> {
                    //Toast(error.getMessage())
                    return null;
                });
        future.thenAccept(response -> {
            if(response!=null){
                Toast.makeText(activity, "Friend added successful " , Toast.LENGTH_SHORT).show();
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
    private ContactForRoom createContactRoom(Contact contact){
        if (contact.getLastMessage()==null){
            contact.setLastMessage(new LastMessage(0,"",""));
        }
        ContactForRoom temp = new ContactForRoom(contact.getId(),contact.getUser().getUsername(),
                contact.getUser().getDisplayName(),contact.getUser().getProfilePic(),contact.getLastMessage().getId(),
                contact.getLastMessage().getCreated(),contact.getLastMessage().getContent());
        return temp;
    }
    public void initializeChats(List<Contact> list,String token) {
        for (Contact contact : list) {
            CompletableFuture<Chat> future = chatApi.getMessages("bearer " + token, contact.getId())
                    .thenApply(messages -> messages).exceptionally(error -> {
                        //Toast(error.getMessage())
                        return null;
                    });
            future.thenAccept(messages -> {
                if (messages != null) {
                    chatRoomDao.insert(messages);
                }
            });
        }
    }
}