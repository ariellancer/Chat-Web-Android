package com.example.exe3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exe3.R;

public class Adding extends AppCompatActivity {
    ImageView settingsButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_friend);
        settingsButton = findViewById(R.id.settings);
        ImageView ret=findViewById(R.id.returnToContactList);
        ret.setOnClickListener(fun->finish());
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
            }
        });
        Button button = findViewById(R.id.buttonAddFriend);
//        db = Room.databaseBuilder(getApplicationContext(), AppDB.class,"contactsDB1").allowMainThreadQueries().build();
//        contactDao = db.contactDao();

        button.setOnClickListener(view->{
            EditText et = findViewById(R.id.editTextFriendName);
//            ContactInfo u = new ContactInfo(et.getText().toString(),et.getText().toString(),et.getText().toString());
//            LastMessage m= new LastMessage(0,"10:00","hellolololo");
//            Contact c= new Contact(0,u,m);
//            contactDao.insert(c);
            String username=et.getText().toString();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("output", username);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }
}
