package com.color.measurement.from_cp.Utils.ColorAssist;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/12.
 */
public class T {

    /**
     * 显示Toast信息（String 短时间）
     * @param context
     * @param message
     */
    public static void showShort(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast信息（int 短时间）
     * @param context
     * @param message
     */
    public static void showShort(Context context,int message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast信息（String 长时间）
     * @param context
     * @param message
     */
    public static void showLong(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast信息（int 长时间）
     * @param context
     * @param message
     */
    public static void showLong(Context context,int message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 在子线程中显示Toast消息
     * @param activity
     * @param message
     */
    public static  void showOnUI(final Activity activity, final String message){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showShort(activity,message);
            }
        });
    }
}
