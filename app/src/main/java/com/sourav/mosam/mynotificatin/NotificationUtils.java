package com.sourav.mosam.mynotificatin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.sourav.mosam.MainActivity;
import com.sourav.mosam.R;

public class NotificationUtils {

    private static String CHANNEL_ID = "MosamChannel";

    public static void executeNotification(Context context, String title, String text) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "MOASAM Notifiaction", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        builder.setSmallIcon(R.drawable.ic_sun)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(MakeIntent(context))
                .setAutoCancel(true);

        manager.notify(1, builder.build());
    }
    private static PendingIntent MakeIntent(Context context){

        Intent intent = new Intent(context, MainActivity.class);
        return  PendingIntent.getActivity(context,50,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
