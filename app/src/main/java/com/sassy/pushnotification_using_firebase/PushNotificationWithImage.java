package com.sassy.pushnotification_using_firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class PushNotificationWithImage extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("Token update::",s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null)
        {
            final String title = remoteMessage.getNotification().getTitle();
            final String body = remoteMessage.getNotification().getBody();
            String url="";

            if(remoteMessage.getData() != null)
            {

                url = remoteMessage.getData().get("image");
                final String finalurl = url;

                //create thread to load pic frm url
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Picasso.get()
                                .load(finalurl)
                                .into(new Target()
                                {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        Log.e("bitmap0:::", String.valueOf(bitmap));
                                        showNotification(PushNotificationWithImage.this,
                                                title,body, null,bitmap);
                                    }

                                    @Override
                                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                    }
                                });



                    }
                });
            }
            else
                showNotification(PushNotificationWithImage.this,title,body,null,null);
        }


        else
        {
            final String title = remoteMessage.getData().get("title");
            final String body = remoteMessage.getData().get("body");
            String url=remoteMessage.getData().get("image");
            Log.e("Data1::",title+"    "+body+"     "+url);

            //To get a Bitmap image from the URL received(alternative for handler written below)
           /* Bitmap bitmap = getBitmapfromUrl(url);
            Log.e("bitmap1:::", String.valueOf(bitmap));

            showNotification(ImageNotification.this,title,body,null,bitmap);*/


            final String finalurl = url;
            //create thread to load pic frm url
            new Handler(Looper.getMainLooper())
                    .post(new Runnable()
                    {
                        @Override
                        public void run()
                        {

                            Picasso.get()
                                    .load(finalurl)
                                    .into(new Target()
                                    {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                            Log.e("bitmap1:::", String.valueOf(bitmap));
                                            showNotification(PushNotificationWithImage.this,
                                                    title,body, null,bitmap);
                                        }

                                        @Override
                                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                        }

                                        @Override
                                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                                        }
                                    });
                        }
                    });




        }


    }






    private void showNotification(Context context, String title, String body, Intent i,Bitmap bitmap)
    {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = new Random().nextInt();
        String channelId = "vidhi";
        String channelName = "vidhisherathia";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder builder;
        if(bitmap != null){
            builder = new NotificationCompat.Builder(context,channelId)
                    .setSmallIcon(R.mipmap.ic_launcher_vs)
                    .setLargeIcon(bitmap)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setStyle(new NotificationCompat.BigPictureStyle()  //3)in expand view showing whole image
                            .bigPicture(bitmap)
                            .bigLargeIcon(null));
        }
        else{
            builder = new NotificationCompat.Builder(context,channelId)
                    .setSmallIcon(R.mipmap.ic_launcher_vs)
                    .setContentTitle(title)
                    .setContentText(body);
        }

        if(i != null){
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addNextIntent(i);
            PendingIntent pi = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pi);

        }

        manager.notify(notificationId,builder.build());
    }



    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

}
