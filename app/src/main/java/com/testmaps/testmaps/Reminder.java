package com.testmaps.testmaps;

import java.util.TimerTask;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Reminder extends TimerTask {
    Context context;
    public Reminder(Context context1)
    {
        this.context = context1;
    }
    @Override
    public void run()
    {
        SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        String getTitle = sharedPref.getString("titlefornotific", "");
        String getDescription = sharedPref.getString("descriptionfornotific", "");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notific)
                .setContentTitle(getTitle)
                .setContentText(getDescription);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("titlefornotific");
        editor.remove("descriptionfornotific");
        editor.commit();

        // Obtain NotificationManager system service in order to show the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());
    }
}
