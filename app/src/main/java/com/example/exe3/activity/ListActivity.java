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
import androidx.lifecycle.ViewModelProvider;

import com.example.exe3.ContactViewModel;
import com.example.exe3.R;
import com.example.exe3.adapters.CustomListAdapter;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.ContactInfo;
import com.example.exe3.infoToDB.LastMessage;
import com.example.exe3.webService.UserApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
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
//        ArrayList<Contact> users = new ArrayList<>();

        FloatingActionButton fabAddFriend = findViewById(R.id.floating_button);
        fabAddFriend.setOnClickListener(view -> {
            Intent intent = new Intent(this, Adding.class);
            //intent.putExtra("array", users);
//            intent.putExtra("array", users);
            startActivity(intent);
        });


        listView = findViewById(R.id.listOfFriend);
        adapter = new CustomListAdapter(this, new ArrayList<Contact>());
        listView.setAdapter(adapter);
        listView.setClickable(true);

        contactViewModel.get().observe(this,contacts -> {
            if(contacts!=null){
                Toast.makeText(this, "Registration successful22", Toast.LENGTH_SHORT).show();
            }
            adapter.setValue(contacts.);
            adapter.notifyDataSetChanged();

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
}
