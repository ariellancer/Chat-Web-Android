package com.example.exe3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exe3.infoToDB.Message;
import com.example.exe3.R;

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

        LinearLayout linearLayout = convertView.findViewById(R.id.layoutMessage);
        TextView content = convertView.findViewById(R.id.contentMessage);
        TextView timeMessage = convertView.findViewById(R.id.timeMessage);

        if (Objects.equals(message.getSender().getUsername(), "me")) {
            linearLayout.setBackgroundResource(R.drawable.message_sender);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, 0);  // 0 indicates no rule
            linearLayout.setLayoutParams(layoutParams);
        } else {
            linearLayout.setBackgroundResource(R.drawable.message_reciever);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, 1);  // 0 indicates no rule
            linearLayout.setLayoutParams(layoutParams);


        }

        content.setText(message.getContent());
        timeMessage.setText(message.getCreated());

        return convertView;
    }
}
