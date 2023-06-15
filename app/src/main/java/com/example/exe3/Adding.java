package com.example.exe3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.exe3.infoToDB.AppDB;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.ContactDao;
import com.example.exe3.infoToDB.ContactInfo;
import com.example.exe3.infoToDB.LastMessage;

public class Adding extends AppCompatActivity {
    ImageView settingsButton;
    private AppDB db;
    private ContactDao contactDao;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_friend);
        settingsButton = findViewById(R.id.settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
            }
        });
        Button button = findViewById(R.id.buttonAddFriend);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class,"contactsDB1").allowMainThreadQueries().build();
        contactDao = db.contactDao();
        
        button.setOnClickListener(view->{
            EditText et = findViewById(R.id.editTextFriendName);
            ContactInfo u = new ContactInfo(et.getText().toString(),et.getText().toString(),et.getText().toString());
            LastMessage m= new LastMessage(0,"10:00","hellolololo");
            Contact c= new Contact(0,u,m);
            contactDao.insert(c);
            finish();
        });
    }
}
