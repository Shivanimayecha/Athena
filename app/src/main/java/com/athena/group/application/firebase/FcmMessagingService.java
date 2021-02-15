package com.athena.group.application.firebase;

import android.content.Intent;

import com.athena.group.application.Constant;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FcmMessagingService extends FirebaseMessagingService {

    String title;
    String message, redirect_ststus;
    String user_id;
    public static final String NOTIFICATION_CHANNEL_ID = "test_notification";
    public static final int NOTIFICATION_ID = 001;
    public static final String ASSIGN_ORDER = "assign_order";
    public static final String DELIVERED_ORDER = "delivered_order";
    public static final String FLAG = "flag";
    public static final String ADMIN_CONFIRMED_ORDER = "admin_confirmed_order";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            title = remoteMessage.getData().get("title");
            message = remoteMessage.getData().get("message");
            NotificationUtils notificationHandler = new NotificationUtils(getApplicationContext());

            if (remoteMessage.getData().get("flag").equals(FLAG)) {
                user_id = remoteMessage.getData().get("id");
                Intent pushNotification = new Intent(Constant.PUSH_NOTIFICATION);
                //Adding notification data to the intent
                pushNotification.putExtra("message", message);
                pushNotification.putExtra("name", title);
                pushNotification.putExtra("id", user_id);

            } else if (remoteMessage.getData().get("flag").equals(ASSIGN_ORDER)) {
                notificationHandler.sendNotification(remoteMessage);
            } else if (remoteMessage.getData().get("flag").equals(DELIVERED_ORDER)) {
                notificationHandler.sendNotification(remoteMessage);
            } else if (remoteMessage.getData().get("flag").equals(ADMIN_CONFIRMED_ORDER)) {
                notificationHandler.sendNotification(remoteMessage);
            } /*else if (remoteMessage.getData().get("flag").equals(LIKE)) {
                notificationHandler.sendNotification(remoteMessage);
            } else if (remoteMessage.getData().get("flag").equals(COMMENT)) {
                notificationHandler.sendNotification(remoteMessage);
            } else if (remoteMessage.getData().get("flag").equals(SHARE)) {
                notificationHandler.sendNotification(remoteMessage);
            } else if (remoteMessage.getData().get("flag").equals(JOIN_GROUP)) {
                notificationHandler.sendNotification(remoteMessage);
            } else if (remoteMessage.getData().get("flag").equals(ADDGROUP)) {
                notificationHandler.sendNotification(remoteMessage);
            }*/
        }
        //  sendNotification(title,message);
    }

}
