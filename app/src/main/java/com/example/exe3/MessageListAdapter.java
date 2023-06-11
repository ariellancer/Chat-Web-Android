package com.example.exe3;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class MessageListAdapter extends ArrayAdapter<Message> {
    LayoutInflater inflater;
    public MessageListAdapter(Context ctx, ArrayList<Message> messagesArrayList) {
        super(ctx, R.layout.message_style, messagesArrayList);
        this.inflater = LayoutInflater.from(ctx);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Message message = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.message_style, parent, false);
        }
        LinearLayout linearLayout = convertView.findViewById(R.id.timeLastMessage);
        TextView content = convertView.findViewById(R.id.contentMessage);
        TextView timeMessage = convertView.findViewById(R.id.timeMessage);
        if(Objects.equals(message.getSender(), "me")){
            linearLayout.setBackgroundResource(R.drawable.message_sender);

        }else{
            linearLayout.setBackgroundResource(R.drawable.message_reciever);

            //linearLayout.setGravity(Gravity.RIGHT);
        }
        content.setText(message.getContentOfMessage());
        timeMessage.setText(message.getTimeOfMessage());

        return convertView;
    }
}
