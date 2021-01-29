package com.example.ateam_app.common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmClass {

    //manager 에는 (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE) 를 넣는다.
    public static void showPushAlarm(NotificationManager manager, String chnnel_id,
                                     String chnnel_name, Context context,
                                     Class<?> cls, int requestCode,
                                     String title, String text) {
        NotificationCompat.Builder builder = null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if(manager.getNotificationChannel(chnnel_id) == null){
                manager.createNotificationChannel(new NotificationChannel(
                        chnnel_id, chnnel_name, NotificationManager.IMPORTANCE_DEFAULT
                ));
            }
            builder = new NotificationCompat.Builder(context, chnnel_name);
        }else{
            builder = new NotificationCompat.Builder(context);
        }

        Intent intent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getActivity
                (context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setSmallIcon(android.R.drawable.ic_menu_view);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);

        Notification noti = builder.build();
        manager.notify(requestCode, noti);
    }
}
