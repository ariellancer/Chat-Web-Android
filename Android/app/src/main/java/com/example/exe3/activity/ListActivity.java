package com.example.exe3.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.ContactInfo;
import com.example.exe3.service.FireBaseData;
import com.example.exe3.infoToDB.Picture;
import com.example.exe3.webService.UserApi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_FRIEND = 1;

    ContactViewModel contactViewModel;
    ListView listView;
    ImageView logout;
    CustomListAdapter adapter;
    String token;
    String username;
    UserApi userApi;
    String firebaseToken;
    Picture picture;

    private List<Contact> contacts;
    private ArrayList<Chat> chats;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        picture = Picture.getInstance();
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

//        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "contactsDB1").allowMainThreadQueries().build();
//        contactDao = db.contactDao();
        contactViewModel = ContactViewModel.getInstance(getApplicationContext(),token);
        contacts = new ArrayList<>();
        adapter = new CustomListAdapter(this, contacts);
        new Thread(()-> {contactViewModel.getContacts();}).start();



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
                        contactViewModel.deleteContact(idOfContact);
                        // Perform any additional actions after deletion if needed
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();

                return true; // Return true to consume the long click event
            }
        });


        listView.setAdapter(adapter);
        listView.setClickable(true);


        contactViewModel.getLiveContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> newContent) {
//                        Toast.makeText(ListActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                contacts.clear();
                contacts.addAll(newContent);
                adapter.notifyDataSetChanged();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Chats.class);
                intent.putExtra("displayName", contacts.get(i).getUser().getDisplayName());
                intent.putExtra("username", contacts.get(i).getUser().getUsername());
                picture.setPicture(contacts.get(i).getUser().getProfilePic());
                intent.putExtra("id",contacts.get(i).getId());
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(complete -> {
            if(complete.isSuccessful()){
                firebaseToken=complete.getResult().getToken();
                Call<String> callFireBase = userApi.fireBaseTokenGenerate(
                        new FireBaseData(username,firebaseToken),token);
                callFireBase.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){

                        }else{
                            Toast.makeText(ListActivity.this, "Failed to connect" + response.code(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ListActivity.this, "Failed to connect" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                });
            }else {
                Toast.makeText(ListActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();
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
                contactViewModel.addContact(getApplicationContext(), outputData);
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
        new Thread(()-> {contactViewModel.getContacts();}).start();
        //contacts.clear();
        //contacts.addAll(contactDao.index()) ;
        //adapter.notifyDataSetChanged();

    }

}



