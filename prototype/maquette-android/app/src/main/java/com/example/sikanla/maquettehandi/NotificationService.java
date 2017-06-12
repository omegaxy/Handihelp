package com.example.sikanla.maquettehandi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {

    public NotificationService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notif_icon)
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setContentText(remoteMessage.getData().get("message"));

        Intent resultIntent = new Intent(this, MainActivity.class);

        if( remoteMessage.getData().get("title").contains("Proposition d'aide") ) {
            resultIntent.putExtra("menuFragment", "NotificationFragment");
        }

        if( remoteMessage.getData().get("title").contains("Demande d'aide") ) {
            resultIntent.putExtra("menuFragment", "InstantFragment");
        }


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());

    }
}
