package com.example.exe3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Chats extends AppCompatActivity {
    final private String[] times = {
            "12:00", "00:30", "03:23", "08:59", "12:23", "22:54", "11:47", "10:04",
    };
    final private String[] contents = {
            "Hi, how are you?", "24K Magic", "Missing Madrid :(", "Wanna hear a joke?", "Yo!",
            "Well....", "Did you see the latest John Wick?",
            "I'm the best!"
    };
    final private String[] sender= {"me","friend"};
    ImageView profilePictureView;
    TextView userNameView;
    ImageView backToList;
    ListView listView;
    MessageListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats_style);

        ArrayList<Message> messages = new ArrayList<>();

        for (int i = 0; i < contents.length; i++) {
            Message message = new Message(
                    contents[i], times[i],sender[i%2]);

            messages.add(message);
        }
        listView = findViewById(R.id.messagesList);
        adapter = new MessageListAdapter(getApplicationContext(), messages);

        listView.setAdapter(adapter);

        profilePictureView = findViewById(R.id.profileUser);
        userNameView = findViewById(R.id.user_Name);
        backToList = findViewById(R.id.returnToListFriend);
        backToList.setOnClickListener(fun -> finish());
        Intent activityIntent = getIntent();

        if (activityIntent != null) {
            String userName = activityIntent.getStringExtra("userName");
            int profilePicture = activityIntent.getIntExtra("profilePicture", R.drawable.blue);

            profilePictureView.setImageResource(profilePicture);
            userNameView.setText(userName);
        }
    }
}
