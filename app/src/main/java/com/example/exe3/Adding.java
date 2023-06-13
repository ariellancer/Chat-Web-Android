package com.example.exe3;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class Adding extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_friend);
//        ArrayList<User> receivedUsers = getIntent().getParcelableArrayListExtra("array");
        Button button = findViewById(R.id.buttonAddFriend);
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
