package com.example.exe3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.exe3.ChatViewModel;
import com.example.exe3.ContactViewModel;
import com.example.exe3.Utilities;
import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Message;
import com.example.exe3.R;
import com.example.exe3.adapters.MessageListAdapter;
import com.example.exe3.infoToDB.NewMessage;
import com.example.exe3.infoToDB.Picture;
import com.example.exe3.service.MessageService;

import java.util.ArrayList;
import java.util.List;

public class Chats extends AppCompatActivity {
    ImageView profilePictureView;
    TextView userNameView;
    ImageView backToList;
    ImageView send;
    ListView listView;
    MessageListAdapter adapter;

    int idChat;

    String displayName;

    String token;
    String profilePicture;
    ChatViewModel viewModel;

    List<Message> messages;
    String username;
    MessageService messageService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats_style);
        Intent activityIntent = getIntent();
        messages = new ArrayList<>();
        adapter = new MessageListAdapter(this, messages);
        send = findViewById(R.id.addMessage);
        profilePictureView = findViewById(R.id.profileUser);
        userNameView = findViewById(R.id.user_Name);
        backToList = findViewById(R.id.returnToListFriend);
        listView = findViewById(R.id.messagesList);

        if (activityIntent != null) {
            idChat = activityIntent.getIntExtra("id",0);
            displayName = activityIntent.getStringExtra("displayName");
            Picture picture = Picture.getInstance();
            token = activityIntent.getStringExtra("token");
            username = activityIntent.getStringExtra("username");
            userNameView.setText(displayName);
            profilePictureView.setImageBitmap(Utilities.bitmapPic(Utilities.extractImage(picture.getPicture())));
        }
        viewModel = ChatViewModel.getInstance(getApplicationContext(),token);
        adapter.setUsername(username);
        new Thread(()->{viewModel.getMessages(idChat);}).start();
        //messageService=new MessageService(viewModel,token,idChat);
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


        send.setOnClickListener(sendTo->
        {
            EditText boxInput = findViewById(R.id.chatInputEditText);
            NewMessage newMessageToSend = new NewMessage(boxInput.getText().toString());
            boxInput.setText("");
            new Thread(()->{
                viewModel.postMessagesById(idChat,newMessageToSend);
            }).start();

        });

        backToList.setOnClickListener(fun -> finish());

    }
}
