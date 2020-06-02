package com.sassy.pushnotification_using_firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //showNotification(remoteMessage.getNotification().getBody());

    }

    public void showNotification(String msg)
    {
        Log.e("Message::",msg);
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,
                0,intent,0);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_vs)
                .setContentTitle("Vidhi Sherathia")
                .setContentText(msg)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,notification);
    }




}
