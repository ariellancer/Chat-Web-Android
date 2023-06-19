package com.example.exe3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.exe3.ContactViewModel;
import com.example.exe3.Utilities;
import com.example.exe3.infoToDB.AppDB;
import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.ChatDao;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.ContactInfo;
import com.example.exe3.infoToDB.LastMessage;
import com.example.exe3.infoToDB.Message;
import com.example.exe3.R;
import com.example.exe3.adapters.MessageListAdapter;
import com.example.exe3.infoToDB.NewMessage;
import com.example.exe3.webService.UserApi;

import java.util.ArrayList;
import java.util.List;

public class Chats extends AppCompatActivity {
    final private String[] times = {
            "12:00", "00:30", "03:23", "08:59", "12:23", "22:54", "11:47", "10:04","12:00", "00:30", "03:23", "08:59", "12:23", "22:54", "11:47", "10:04",
    };
    final private String[] contents = {
            "Hi, how are you?", "24K Magic", "Missing Madrid :(", "Wanna hear a joke?", "Yo!",
            "Well....", "Did you see the latest John Wick?",
            "I'm the best!","Hi, how are you?", "24K Magic", "Missing Madrid :(", "Wanna hear a joke?", "Yo!",
            "Well....", "Did you see the latest John Wick?",
            "I'm the best!"
    };
    final private String[] sender= {"me","friend"};
    ImageView profilePictureView;
    TextView userNameView;
    ImageView backToList;
    ImageView send;
    ListView listView;
    MessageListAdapter adapter;
    private AppDB db;
    private ChatDao chatDao;

    int idChat;

    String displayName;

    String token;
    String profilePicture;

    ContactViewModel viewModel;

    List<Message> messages;
    String username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats_style);
        Intent activityIntent = getIntent();
        viewModel = new ContactViewModel();
        messages = new ArrayList<>();
        adapter = new MessageListAdapter(this, messages);
        send = findViewById(R.id.addMessage);
        profilePictureView = findViewById(R.id.profileUser);
        userNameView = findViewById(R.id.user_Name);
        backToList = findViewById(R.id.returnToListFriend);
        listView = findViewById(R.id.messagesList);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "chatsDB1").allowMainThreadQueries().build();
        chatDao = db.chatDao();
//        ImageView imageView=findViewById(R.id.addMessage);
//        imageView.setOnClickListener(view->{
//            EditText et = findViewById(R.id.chatInputEditText);
//
//            Message m= new Message(0,"10:00",new Message.Sender("ARIEL"),et.getText().toString());
//            Contact c= new Contact(0,u,m);
//            contactDao.insert(c);
//            finish();
//        });
        if (activityIntent != null) {
            idChat = activityIntent.getIntExtra("id",0);
            displayName = activityIntent.getStringExtra("displayName");
            profilePicture = activityIntent.getStringExtra("profilePicture");
            token = activityIntent.getStringExtra("token");
            username = activityIntent.getStringExtra("username");
            //profilePictureView.setImageBitmap(ListActivity.bitmapPic(profilePicture));
            userNameView.setText(displayName);
            profilePictureView.setImageBitmap(Utilities.bitmapPic(Utilities.extractImage(profilePicture)));

//            Chat chat=chatDao.get(idChat);
//            messages.addAll(chat.getMessages());
        }
        adapter.setUsername(username);
        new Thread(()-> {viewModel.getMessages(token,idChat);}).start();
        viewModel.getLiveMessages().observe(this, new Observer<Chat>() {
            @Override
            public void onChanged(Chat newMessages) {
                messages.clear();
                messages.addAll(newMessages.getMessages());
                adapter.notifyDataSetChanged();
            }
        });
        listView.setAdapter(adapter);
        listView.setClickable(true);

//        for (int i = 0; i < contents.length; i++) {
//            Message.Sender info = new Message.Sender(sender[i%2]);
//            Message message = new Message(i,times[i],info,contents[i]);
//
//            messages.add(message);
//        }

        send.setOnClickListener(sendTo->
        {
            EditText boxInput = findViewById(R.id.chatInputEditText);
            NewMessage newMessageToSend = new NewMessage(boxInput.getText().toString());
            boxInput.setText("");
            new Thread(()->{
                viewModel.postMessagesById(token,idChat,newMessageToSend);
            }).start();

        });

        backToList.setOnClickListener(fun -> finish());

    }
}
