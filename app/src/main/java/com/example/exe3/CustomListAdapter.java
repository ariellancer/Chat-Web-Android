package com.example.exe3;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import androidx.annotation.Nullable;
public class CustomListAdapter extends ArrayAdapter<User> {
    LayoutInflater inflater;
    public CustomListAdapter(Context ctx, ArrayList<User> userArrayList) {
        super(ctx, R.layout.contact_style, userArrayList);
        this.inflater = LayoutInflater.from(ctx);

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        User user = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_style, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.profileImage);
        TextView userName = convertView.findViewById(R.id.friendName);
        TextView lastMsg = convertView.findViewById(R.id.lastMessage);
        TextView time = convertView.findViewById(R.id.timeLastMessage);

        imageView.setImageResource(user.getPictureId());
        userName.setText(user.getUserName());
        lastMsg.setText(user.getLastMassage());
        time.setText(user.getLastMassageSendingTime());

        return convertView;
    }
}
