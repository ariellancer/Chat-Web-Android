package com.example.exe3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Chats extends AppCompatActivity {
    ImageView profilePictureView;
    TextView userNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats_style);

        profilePictureView = findViewById(R.id.profileUser);
        userNameView = findViewById(R.id.user_Name);

        Intent activityIntent = getIntent();

        if (activityIntent != null) {
            String userName = activityIntent.getStringExtra("userName");
            int profilePicture = activityIntent.getIntExtra("profilePicture", R.drawable.blue);

            profilePictureView.setImageResource(profilePicture);
            userNameView.setText(userName);
        }
    }
}
