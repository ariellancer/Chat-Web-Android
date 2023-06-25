package com.example.exe3.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModel;

import com.example.exe3.ContactViewModel;
import com.example.exe3.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MessageService extends FirebaseMessagingService {
    static final String Channel_ID = "1";
    ContactViewModel contactViewModel;
    String token;
    int id;
    int idOfSender;


    public MessageService(ContactViewModel viewModel, String token, int id) {
        this.contactViewModel = viewModel;
        this.id = id;
        this.token = token;
    }

//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        idOfSender = Integer.parseInt(remoteMessage.getData().get("id"));

        if (id == idOfSender) {
            contactViewModel.getMessages(token, id);
        }
        createNotificationChannel();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Channel_ID)
                .setSmallIcon(R.drawable.favicon)
                .setContentTitle(remoteMessage.getData().get("sender"))
                .setContentText(remoteMessage.getData().get("content"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        int notificationId = 1;
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(notificationId, builder.build());
    }


}

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification);
            String description = getString(R.string.this_is_the_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Channel_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}