package com.example.taplalkozaskoveto;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private static final String CHANNEL_ID = "food_notification_channel";
    private static final int NOTIFICATION_ID = 10;
    private NotificationManager mManager;
    private Context mContext;

    public NotificationHandler(Context context) {
        this.mContext = context;
        this.mManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel();
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return;
        }
        NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"Nutrition Notification", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.BLUE);
        channel.setDescription("Notificiation from Nutrition Follower");
        this.mManager.createNotificationChannel(channel);
    }

    public void send(String message){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(mContext,CHANNEL_ID)
                .setContentTitle("Nutrition Follower")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_food);

        this.mManager.notify(NOTIFICATION_ID,builder.build());
    }
}
