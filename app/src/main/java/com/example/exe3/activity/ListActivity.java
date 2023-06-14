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
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(fun -> finish());
        ArrayList<Contact> users = new ArrayList<>();

        FloatingActionButton fabAddFriend = findViewById(R.id.floating_button);
        fabAddFriend.setOnClickListener(view -> {
            Intent intent = new Intent(this, Adding.class);
            //intent.putExtra("array", users);
//            intent.putExtra("array", users);
            startActivity(intent);
        });
        for (int i = 0; i < profilePictures.length; i++) {
            ContactInfo info = new ContactInfo(userNames[i],userNames[i],"a");
            LastMessage lastMessage = new LastMessage(i,times[i],lastMassages[i]);
            Contact aUser = new Contact(i,info,lastMessage);

            users.add(aUser);
        }

        listView = findViewById(R.id.listOfFriend);
        adapter = new CustomListAdapter(getApplicationContext(), users);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Chats.class);

                intent.putExtra("userName", userNames[i]);
                intent.putExtra("profilePicture", profilePictures[i]);
                intent.putExtra("lastMassage", lastMassages[i]);
                intent.putExtra("time", times[i]);

                startActivity(intent);
            }
        });
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
