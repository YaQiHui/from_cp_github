package com.color.measurement.from_cp.Manager.SP;

import android.content.SharedPreferences;

/**
 * Created by Gzw on 2016/8/8.
 */
public class MySharedPreferences {
//代理


    public static final String IS_REGISTER = "isRegister";
    public static final String USER_PHONE = "userPhone";
    public static final String CONNECTION_INSTRUMENT = "connection_instrument";
    public static final String LIGHTING_VALUES = "lighting_values";
    public static final String OBSERVED_VALUES = "observed_values";
    public static final String USER_INFO_FILE = "user_info";
    public static final String SETTING_INFO = "setting_info";


    private SharedPreferences mUserInfo;            //用来存用户信息
    private SharedPreferences mSettingInfo;         //用来存设置信息
    private static MySharedPreferences instance;

    //单例模式，双重检测
    public static MySharedPreferences getInstance() {
        if (instance == null) {
            synchronized (MySharedPreferences.class) {
                if (instance == null) {
                    instance = new MySharedPreferences();
                }
            }
        }
        return instance;
    }

    public SharedPreferences getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(SharedPreferences userInfo) {
        this.mUserInfo = userInfo;
    }

    public SharedPreferences getSettingInfo() {
        return mSettingInfo;
    }

    public void setSettingInfo(SharedPreferences settingInfo) {
        mSettingInfo = settingInfo;
    }

    //是否连接过设备
    public boolean isConnectionInstrument() {
        return getUserInfo().getBoolean(CONNECTION_INSTRUMENT, false);
    }

    //是否注册过
    public boolean isRegister() {
        return getUserInfo().getBoolean(IS_REGISTER, false);
    }

    //观察值
    public int getObservedValues() {
        return getSettingInfo().getInt(OBSERVED_VALUES, 1);
    }

    //照度值
    public int getLightingValues() {
        return getSettingInfo().getInt(LIGHTING_VALUES, 4);
    }
}
