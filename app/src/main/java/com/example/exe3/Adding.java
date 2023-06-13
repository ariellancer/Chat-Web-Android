package com.example.exe3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
public class Adding extends AppCompatActivity {
    ImageView settingsButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_friend);
//        ArrayList<User> receivedUsers = getIntent().getParcelableArrayListExtra("array");
        Button button = findViewById(R.id.buttonAddFriend);
        settingsButton = findViewById(R.id.settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(view->{
//            EditText text=findViewById(R.id.editTextFriendName);
//            String userName=text.getText().toString();
//            User aUser = new User(
//                    userName, R.drawable.omer,
//                    "Hi, how are you?", "10:04"
//            );
//            receivedUsers.add(aUser);
            finish();
        });

       // finish();

    }
}
