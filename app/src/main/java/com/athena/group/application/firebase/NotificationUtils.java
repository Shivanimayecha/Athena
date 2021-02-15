package com.athena.group.application.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.athena.group.R;
import com.athena.group.Truck__Driver_Panel.SingleAssignOrderActivity;
import com.athena.group.orderHistory.SingleOpenOrderActivity;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {
    private Context mContext;
    String title, flag;
    String message, redirect_ststus;
    String user_id;
    public static final String NOTIFICATION_CHANNEL_ID = "test_notification";
    public static final int NOTIFICATION_ID = 001;
    PendingIntent pendingIntent;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    //public void sendNotification(String titlee,String messagee,String flag,String redirect_status) {
    public void sendNotification(RemoteMessage remoteMessage) {

        title = remoteMessage.getData().get("title");
        message = remoteMessage.getData().get("message");
        flag = remoteMessage.getData().get("flag");

        Log.e("TAG", "title: -- msg--flag-" + title + "--" + message + " --" + flag);
        createNotificationChannel(message);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.appicon);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(message);
        builder.setStyle(bigTextStyle);
        builder.setColor(mContext.getResources().getColor(R.color.colorPrimary));

        if (flag.equals("assign_order")) {
            Log.e("TAG", "assign--: " + flag);
            Intent i = new Intent(mContext, SingleAssignOrderActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("data_ord_id", remoteMessage.getData().get("ord_id"));
            i.putExtra("data_assign_ord_id", remoteMessage.getData().get("assign_ord_id"));
            pendingIntent = PendingIntent.getActivity(mContext, 0, i, PendingIntent.FLAG_ONE_SHOT);
        } else if (flag.equals("delivered_order")) {
            Log.e("TAG", "delivered--: " + flag);
            Intent i = new Intent(mContext, SingleOpenOrderActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("data_delivered_ord_id", remoteMessage.getData().get("ord_id"));
            pendingIntent = PendingIntent.getActivity(mContext, 0, i, PendingIntent.FLAG_ONE_SHOT);
        } else if (flag.equals("admin_confirmed_order")) {
            Log.e("TAG", "admin_confirmed_order--: " + flag);
            Intent i = new Intent(mContext, SingleOpenOrderActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("data_delivered_ord_id", remoteMessage.getData().get("ord_id"));
            pendingIntent = PendingIntent.getActivity(mContext, 0, i, PendingIntent.FLAG_ONE_SHOT);
        }

        Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.appicon);
        builder.setLargeIcon(bm);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mContext);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel(String description) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test_notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


}
