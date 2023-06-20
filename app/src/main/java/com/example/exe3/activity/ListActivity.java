package com.example.exe3.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.exe3.ContactViewModel;
import com.example.exe3.R;
import com.example.exe3.Utilities;
import com.example.exe3.adapters.CustomListAdapter;
import com.example.exe3.infoToDB.AppDB;
import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.ContactDao;
import com.example.exe3.infoToDB.ContactInfo;
import com.example.exe3.webService.UserApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_FRIEND = 1;

    ContactViewModel contactViewModel;
    ListView listView;
    ImageView logout;
    CustomListAdapter adapter;
    String token;
    String username;
    UserApi userApi;

    private AppDB db;
    private ContactDao contactDao;
    private List<Contact> contacts;
    private ArrayList<Chat> chats;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent activityIntent = getIntent();
        if (activityIntent != null) {
            token = activityIntent.getStringExtra("token");
            username = activityIntent.getStringExtra("username");
            userApi = UserApi.getInstance();
            getUsernameInfo();

        }
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(fun -> finish());
        listView = findViewById(R.id.listOfFriend);


        contactViewModel = new ContactViewModel();
        contacts = new ArrayList<>();
        adapter = new CustomListAdapter(this, contacts);
        new Thread(()-> {contactViewModel.getContacts(token);}).start();

//        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "contactsDB1").allowMainThreadQueries().build();
//        contactDao = db.contactDao();

        FloatingActionButton fabAddFriend = findViewById(R.id.floating_button);
        fabAddFriend.setOnClickListener(view -> {
            Intent intent = new Intent(this, Adding.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_FRIEND);
        });

        chats = new ArrayList<>();



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete this contact?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Contact curr = contacts.remove(position);
                        int idOfContact = curr.getId();
                        contactViewModel.deleteContact(token, idOfContact);
                        // Perform any additional actions after deletion if needed
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();

                return true; // Return true to consume the long click event
            }
        });



//        (adapterView, view, i, l) -> {
//
////            contactDao.delete(curr);
////            adapter.notifyDataSetChanged();
//            return ;
//        });
        listView.setAdapter(adapter);
        listView.setClickable(true);


        contactViewModel.get().observe(this, new Observer<List<Contact>>() {
                    @Override
                    public void onChanged(List<Contact> newContent) {
                        Toast.makeText(ListActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        contacts.clear();
                        contacts.addAll(newContent);
                        adapter.notifyDataSetChanged();
                    }
                });

//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                Intent intent = new Intent(getApplicationContext(), Chats.class);
////
////                intent.putExtra("id", userNames[i]);
////                intent.putExtra("profilePicture", profilePictures[i]);
////                intent.putExtra("lastMassage", lastMassages[i]);
////                intent.putExtra("time", times[i]);
////
////                startActivity(intent);
//
//
//                        Intent intent = new Intent(getApplicationContext(), Chats.class);
//                        int id = -1;
//                        List<ContactInfo> users;
//                        List<Message> messages;
//                          ContactInfo inf = contacts.get(i).getUser();
//                        for (int j = 0; j < chats.size(); j++) {
//                            if (chats.get(j).getUsers().get(0) == inf || chats.get(j).getUsers().get(1) == inf) {
//                                id = chats.get(j).getId();
//                                users = chats.get(j).getUsers();
//                                messages = chats.get(j).getMessages();
//                            }
//                        }
//                        intent.putExtra("id", id);
//                        intent.putExtra("userName", inf.getDisplayName());
//                        intent.putExtra("profilePicture", inf.getProfilePic());
//
//
////            List<ContactInfo> users = chats.get(i).getUsers();
////            arrayAdapter.notifyDataSetChanged();
//                        startActivity(intent);
//
//                    }
//                });


//        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Chats.class);
                intent.putExtra("displayName", contacts.get(i).getUser().getDisplayName());
                intent.putExtra("username", contacts.get(i).getUser().getUsername());
                intent.putExtra("profilePicture", contacts.get(i).getUser().getProfilePic());
                intent.putExtra("id",contacts.get(i).getId());
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_FRIEND && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String outputData = data.getStringExtra("output");
//                String fixedToken= "bearer " +token;
                contactViewModel.addContact(getApplicationContext(), outputData,token);
                 // Do something with the output data here
            }
        }
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
                imageView.setImageBitmap(Utilities.bitmapPic(Utilities.extractImage(contactInfo.getProfilePic())));
                userName.setText(contactInfo.getDisplayName());
            }
        });

    }
        @Override
    protected void onResume() {
        super.onResume();
        new Thread(()-> {contactViewModel.getContacts(token);}).start();
        //contacts.clear();
        //contacts.addAll(contactDao.index()) ;
        //adapter.notifyDataSetChanged();

    }

    }




