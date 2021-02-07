package com.example.ateam_app.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.ateam_app.MainActivity;
import com.example.ateam_app.R;
import com.example.ateam_app.user_pakage.LoginActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class AteamFirebaseMessagingService extends FirebaseMessagingService {
    //푸시 알림 설정
    private String title = "";
    private String body = "";
    private String color = "";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //푸시알림 메세지 분기
        //putData를 사용했을때 data가져오기
        if(remoteMessage.getData().size() > 0){
            title = remoteMessage.getData().get("title");
            body = remoteMessage.getData().get("body");
            color = remoteMessage.getData().get("color");

            if(true){
                scheduleJob();
            }else{
                hadleNow();
            }
        }

        //Notification 사용했을때 data 가져오기
        if(remoteMessage.getNotification() != null){
            Log.d("메세지", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        sendNotification();
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        sendRegistrationToServer(token);
    }

    private void scheduleJob() {
        OneTimeWorkRequest work = new OneTimeWorkRequest
                .Builder(AteamWorker.class).build();
        WorkManager.getInstance(this).beginWith(work).enqueue();
    }

    private void hadleNow() {

    }

    private void sendRegistrationToServer(String token) {
        //앱 서버에 토큰을 보내려면 이 메서드를 구현한다.
    }

    private void sendNotification() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2000 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "alramService";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(android.R.drawable.ic_menu_view)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setColor(Color.parseColor(color))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(2000 /* ID of notification */, notificationBuilder.build());
    }
}