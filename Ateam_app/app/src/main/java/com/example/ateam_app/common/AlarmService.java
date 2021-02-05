package com.example.ateam_app.common;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.ateam_app.irdnt_list_package.IrdntLifeEndNumATask;
import com.example.ateam_app.user_pakage.LoginActivity;

public class AlarmService extends Service {
    IrdntLifeEndNumATask irdntLifeEndNumATask;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        processCommand();

        return START_STICKY;
    }

    private void processCommand() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        AlarmClass.showPushAlarm((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE),
                "alramService",
                "alramService",
                AlarmService.this,
                LoginActivity.class,
                2000,
                "유통기한 알림!",
                "유통기한이 임박한 재료가 있습니다!");
    }
}