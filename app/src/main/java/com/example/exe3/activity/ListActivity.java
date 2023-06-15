package com.example.exe3.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.lifecycle.ViewModelProvider;

import com.example.exe3.ContactViewModel;
import com.example.exe3.R;
import com.example.exe3.adapters.CustomListAdapter;
import com.example.exe3.infoToDB.AppDB;
import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.ContactDao;
import com.example.exe3.infoToDB.ContactInfo;
import com.example.exe3.infoToDB.Message;
import com.example.exe3.infoToDB.LastMessage;
import com.example.exe3.webService.UserApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    ContactViewModel contactViewModel;
    ListView listView;
    ImageView logout;
    CustomListAdapter adapter;
    String token;
    String username;
    UserApi userApi;
    private AppDB db;
    private ContactDao contactDao;
    private ArrayList<Contact> contacts;
    private ArrayList<Chat> chats;
    private CustomListAdapter arrayAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent activityIntent = getIntent();
        if(activityIntent!=null) {
            token = activityIntent.getStringExtra("token");
            username = activityIntent.getStringExtra("username");
            userApi = new UserApi();
            getUsernameInfo();

        }

        contactViewModel=new ViewModelProvider(this).get(ContactViewModel.class);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(fun -> finish());
      //  ArrayList<Contact> users = new ArrayList<>();
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "contactsDB1").allowMainThreadQueries().build();
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

        listView = findViewById(R.id.listOfFriend);
        adapter = new CustomListAdapter(this, new ArrayList<Contact>());
        listView.setAdapter(adapter);
        listView.setClickable(true);


        //listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getApplicationContext(), Chats.class);
//
//                intent.putExtra("id", userNames[i]);
//                intent.putExtra("profilePicture", profilePictures[i]);
//                intent.putExtra("lastMassage", lastMassages[i]);
//                intent.putExtra("time", times[i]);
//
//                startActivity(intent);


                Intent intent = new Intent(getApplicationContext(), Chats.class);
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
                intent.putExtra("id",id);
                intent.putExtra("userName",inf.getDisplayName());
                intent.putExtra("profilePicture",inf.getProfilePic());
        contactViewModel.get().observe(this,contacts -> {
            if(contacts!=null){
                Toast.makeText(this, "Registration successful22", Toast.LENGTH_SHORT).show();
            }
            adapter.setValue(contacts.);
            adapter.notifyDataSetChanged();

//            List<ContactInfo> users = chats.get(i).getUsers();
//            arrayAdapter.notifyDataSetChanged();
                startActivity(intent);

        });


//        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getApplicationContext(), Chats.class);
//
//                intent.putExtra("userName", userNames[i]);
//                intent.putExtra("profilePicture", profilePictures[i]);
//                intent.putExtra("lastMassage", lastMassages[i]);
//                intent.putExtra("time", times[i]);
//
//                startActivity(intent);
//            }
//        });
    }
    private void getUsernameInfo() {
        // Do something with the processed result
        CompletableFuture<ContactInfo> future = userApi.getUsernameInfo( "bearer "+ token, username)
                .thenApply(contactInfo -> contactInfo)
                .exceptionally(error -> {
                    //Toast(error.getMessage())
                    return null;
                });
        future.thenAccept(contactInfo -> {
            if (contactInfo != null) {
                ImageView imageView = findViewById(R.id.profileImageUser);
                TextView userName = findViewById(R.id.nameOfUser);
                imageView.setImageBitmap(bitmapPic(extractImage(contactInfo.getProfilePic())));
                userName.setText(contactInfo.getDisplayName());
            }
        });

    }

    public Bitmap bitmapPic(String img) {
        byte[] decodedBytes = Base64.decode(img, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    public String extractImage(String webBase64) {
        int start = webBase64.indexOf(",");
        return webBase64.substring(start +1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contacts.clear();
        contacts.addAll(contactDao.index()) ;
        arrayAdapter.notifyDataSetChanged();

    }
}

