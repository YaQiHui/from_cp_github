package com.color.measurement.from_cp.UI;

import android.app.Application;

import com.color.measurement.from_cp.UI.Activitys.instrument_600.TableC.AddTitleDialog;

/**
 * Created by wpc on 2016/8/29.
 */
public class app extends Application {

    public static String changed_name;
    public static String changed_tips;

    public static boolean[] check_state = new boolean[AddTitleDialog.type.length];
    public static   String table_name;
    public static boolean check_atm_name=false;
    public static String BLE_drive_name;
    public static String BLE_drive_address;

    //短信验证码
    public static final String ACCOUNT_SID = "dc3b51d0e707bb5944cfa58a7f2345a0";
    public static final String APP_ID = "0c6dae9358e643aba75642b6b6b73852";
    public static final String TEMPLATE_ID = "27659";
    public static final String AUTH_TOKEN = "1e0ebc612e58d31b7715a09ccc9fab23";


    //shareSDK的
    public static final String APP_KEY = "15a79d2fb71ea";
    public static int oldcolor = 0;
    public static int color = 0;
    public static String str_color = "";


    @Override
    public void onCreate() {
        check_state[0] = true;
//        ShareSDK.initSDK(this, APP_KEY);
        super.onCreate();
    }
}
