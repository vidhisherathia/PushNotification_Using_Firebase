package com.sassy.pushnotification_using_firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MultipleMessagingService extends FirebaseMessagingService {

    View view;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        /*showNotification(remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody());*/

    }

    public void showNotification(String title,String msg)
    {
        Intent intent = new Intent(view.getContext(),MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(view.getContext(),0,intent,0);

        Notification notification = new NotificationCompat.Builder(view.getContext())
                .setSmallIcon(R.mipmap.ic_launcher_vs)
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager manager = (NotificationManager) view.getContext().getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,notification);
    }

}
