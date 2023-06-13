package com.example.exe3.adapters;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import androidx.annotation.Nullable;

import com.example.exe3.R;
import com.example.exe3.infoToDB.Contact;

public class CustomListAdapter extends ArrayAdapter<Contact> {
    LayoutInflater inflater;
    public CustomListAdapter(Context ctx, ArrayList<Contact> userArrayList) {
        super(ctx, R.layout.contact_style, userArrayList);
        this.inflater = LayoutInflater.from(ctx);

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contact contact = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_style, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.profileImage);
        TextView userName = convertView.findViewById(R.id.friendName);
        TextView lastMsg = convertView.findViewById(R.id.lastMessage);
        TextView time = convertView.findViewById(R.id.timeLastMessage);
        //imageView.setImageResource(contact.getUser().getProfilePic());
        userName.setText(contact.getUser().getUsername());
        lastMsg.setText(contact.getLastMassage().getContent());
        time.setText(contact.getLastMassage().getCreated());

        return convertView;
    }
}