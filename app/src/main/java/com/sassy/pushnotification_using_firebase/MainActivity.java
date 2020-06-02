package com.sassy.pushnotification_using_firebase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*

        * Necassary steps to be followed-
        *
        * 1.Create a project for your application in your firebase console.
        * 2.Add you package name and SHA1 key.
        * 3.Download google-json file and add it to project>>app(paste it here)
        * 4.Go to tools >> firebase (assistant will appear) >> cloud messaging(follow the procedure)
        * 5.Add all the necessary dependencies during the above procedure as instructed.
        *
        * We basically are exploring 3 features -
        *       1.Simple messaging service
        *       2.Multiple messaging service
        *       3.Notification with image
        * Make sure to comment out other code while exploring particular feature
        *
        *
        * */





        /*...............for MultipleMessagingService class enable this part..........*/

       /* NotificationChannel notificationChannel = new NotificationChannel(
                "MultipleNotification",
                "MultipleNotification",
                NotificationManager.IMPORTANCE_DEFAULT);

        //Subscribe the client app to a topic
        //send diff notifications based upon paid,logged in,overall user
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "msg_subscribed";
                        if (!task.isSuccessful()) {
                            msg = "msg_subscribe_failed";
                        }
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });*/


        /*...............for MultipleMessagingService class enable this part..........*/











        /*................for PushNotificationWithImage class enable this part..............*/


        /*
        Steps to be followed -

        1.Write the respective code from MainActivity and PushNotificationWithImage
        2.Install the application.After installation you will receive token from console.
        3.Open postman/adv rest client
        4.Generate POST request with request url = "https://fcm.googleapis.com/fcm/send"
        5.In header add content-type=application/json and
          authorization as key="your_cloud_messaging_server_key"
        (to get server key open your corresponding firebase project >> project settings
         >> cloud messaging >> server key)
        6.Now in body select raw option and type following -
        {
            "data":{
            "title":"New notification with Image",
                    "body":"This is msg with picture",
                    "image":"image_url"
        },
            "to":"your_generated_token"
        }
        7.Now send the request and you will receive notification

        */

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful())
                            Log.d("TOKEN_UPDATE::",task.getResult().getToken());

                    }
                });

        /*................for PushNotificationWithImage class enable this part..............*/


    }
}
