package com.example.exe3.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModel;

import com.example.exe3.ChatViewModel;
import com.example.exe3.ContactViewModel;
import com.example.exe3.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MessageService extends FirebaseMessagingService {
    static final String Channel_ID = "1";
    ChatViewModel chatViewModel;

    ContactViewModel contactViewModel;
    int idOfSender;
    int idChat;

//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if(remoteMessage.getData().get("content").equals("delete")){
            idOfSender = Integer.parseInt(remoteMessage.getData().get("id"));
            chatViewModel = ChatViewModel.getInstance();
            contactViewModel = ContactViewModel.getInstance();
            if (contactViewModel!=null){
                new Thread(()->{contactViewModel.getContacts();}).start();

            }
            if (chatViewModel!=null){
                idChat=chatViewModel.getIdChat();
                if(idChat==idOfSender){
                    new Thread(()->{chatViewModel.getMessages(idOfSender);}).start();}

            }
        }else{

        idOfSender = Integer.parseInt(remoteMessage.getData().get("id"));
        chatViewModel = ChatViewModel.getInstance();
        contactViewModel = ContactViewModel.getInstance();
        if (contactViewModel!=null){
            new Thread(()->{contactViewModel.getContacts();}).start();
                createNotification(remoteMessage.getData().get("sender"),
                        remoteMessage.getData().get("content"));
        }
        if (chatViewModel!=null){
            idChat=chatViewModel.getIdChat();
            if(idChat==idOfSender){
                new Thread(()->{chatViewModel.getMessages(idOfSender);}).start();}
//            }else
//            {
//                if(contactViewModel==null){
//                    createNotification(remoteMessage);
//                }
//
//            }
            }
        }
        }

//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.notification);
//            String description = getString(R.string.this_is_the_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(Channel_ID, name, importance);
//            channel.setDescription(description);
//
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//    private void createNotification(@NonNull RemoteMessage remoteMessage){
//        createNotificationChannel();
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Channel_ID)
//                .setSmallIcon(R.drawable.favicon)
//                .setContentTitle(remoteMessage.getData().get("sender"))
//                .setContentText(remoteMessage.getData().get("content"))
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        int notificationId = 1;
//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        notificationManager.notify(notificationId, builder.build());
//    }
private void createNotification(String title, String body) {
    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    // Check if the device is running Android Oreo (API 26) or higher
    String channelId = "1";
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create a notification channel
//        channelId = getString(R.string.channelMessageId);
        CharSequence channelName = getString(R.string.notification);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel =
                new NotificationChannel(channelId, channelName, importance);
        notificationManager.createNotificationChannel(channel);
    }

    // Build the notification
    NotificationCompat.Builder builder =
            new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);

    // Show the notification
    notificationManager.notify(Integer.parseInt(channelId), builder.build());
}
}