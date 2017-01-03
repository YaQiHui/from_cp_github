package com.color.measurement.from_cp.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.RemoteViews;

import com.color.measurement.from_cp.R;

/**
 * Created by wpc on 2016/9/9.
 */
public class NotificationUtils {

    private static final int NOTIFICATION_ID_BASIC=0;

   public static void  showBaseNotification(Context context){
        Notification.Builder builder=new Notification.Builder(context);
        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.baidu.com"));
        PendingIntent pendingInent=PendingIntent.getActivity(context,0,intent,0);
        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentIntent(pendingInent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_notifications_black_24dp));
        builder.setContentTitle("Basic Notifications");
        builder.setContentText("i am a basic notification");
        builder.setSubText("it is really basic");
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID_BASIC,builder.build());
    }

    public static void showNotification(Context context) {//折叠式通知
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sina.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, it, 0);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_black_24dp));
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);
        contentView.setTextViewText(R.id.tv_notification, "show me when collapsed");
        Notification notification = builder.build();
        notification.contentView = contentView;
        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.notification_expanded);
        notification.bigContentView = expandedView;

        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, notification);
    }
}
