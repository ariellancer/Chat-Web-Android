package com.example.exe3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.exe3.Adding;
import com.example.exe3.R;
import com.example.exe3.adapters.CustomListAdapter;
import com.example.exe3.infoToDB.AppDB;
import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.ContactDao;
import com.example.exe3.infoToDB.ContactInfo;
import com.example.exe3.infoToDB.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
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
    ImageView logout;
    CustomListAdapter adapter;
    private AppDB db;
    private ContactDao contactDao;
    private ArrayList<Contact> contacts;
    private ArrayList<Chat> chats;
    private CustomListAdapter arrayAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(fun -> finish());
      //  ArrayList<Contact> users = new ArrayList<>();
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "contactsDB").allowMainThreadQueries().build();
        contactDao = db.contactDao();

        FloatingActionButton fabAddFriend = findViewById(R.id.floating_button);
        fabAddFriend.setOnClickListener(view -> {
            Intent intent = new Intent(this, Adding.class);
            startActivity(intent);
        });
//        for (int i = 0; i < profilePictures.length; i++) {
//            ContactInfo info = new ContactInfo(userNames[i],userNames[i],"a");
//            LastMessage lastMessage = new LastMessage(i,times[i],lastMassages[i]);
//            Contact aUser = new Contact(i,info,lastMessage);
//
//            users.add(aUser);
//        }
        contacts = new ArrayList<>();
        chats = new ArrayList<>();
        arrayAdapter = new CustomListAdapter(getApplicationContext(), contacts);
        ListView listView = findViewById(R.id.listOfFriend);
        // adapter = new CustomListAdapter(getApplicationContext(), users);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemLongClickListener((adapterView,view,i,l)->{
            Contact curr = contacts.remove(i);
            contactDao.delete(curr);
            arrayAdapter.notifyDataSetChanged();
            return true;
        });



        //listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Chats.class);

                intent.putExtra("id", userNames[i]);
                intent.putExtra("profilePicture", profilePictures[i]);
                intent.putExtra("lastMassage", lastMassages[i]);
                intent.putExtra("time", times[i]);

                startActivity(intent);
            }
        });
        listView.setOnItemClickListener((adapterView,view,i,l)->{
            Intent intent = new Intent(this, Chats.class);
            int id=-1;
            List<ContactInfo>  users;
            List<Message> messages;
            ContactInfo inf = contacts.get(i).getUser();
            for(int j=0;j<chats.size();j++){
                if(chats.get(j).getUsers().get(0)==inf || chats.get(j).getUsers().get(1)==inf){
                    id=chats.get(j).getId();
                    users=chats.get(j).getUsers();
                    messages=chats.get(j).getMessages();
                }
            }
            List<ContactInfo> users = chats.get(i).getUsers();
            arrayAdapter.notifyDataSetChanged();

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        contacts.clear();
        contacts.addAll(contactDao.index()) ;
        arrayAdapter.notifyDataSetChanged();

    }
}

