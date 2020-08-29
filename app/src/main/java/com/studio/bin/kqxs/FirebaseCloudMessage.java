package com.studio.bin.kqxs;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;


import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseCloudMessage extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.

        String title;
        String body;
       // Log.i("LANGUAGE", remoteMessage.getNotification().getTitle() + "  222");
        if (remoteMessage.getData().isEmpty()) {
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
            sendNotification(title, body);
        }
    }

    /**
     *
     * @param messageTitle
     * @param messageBody
     */
    private void sendNotification(String messageTitle, String messageBody) {
        Intent intent = new Intent(this, ChooseOptionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */,intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri u = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .setSound(u)
                .build();
         startForeground(1, notification);
    }


}
