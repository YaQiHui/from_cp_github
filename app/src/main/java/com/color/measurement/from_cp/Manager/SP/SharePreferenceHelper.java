package com.color.measurement.from_cp.Manager.SP;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wpc on 2016/11/14.
 */

public class SharePreferenceHelper {

    static SharedPreferences mSharedPreferences;

    public static SharedPreferences init(Activity context) {
        Log.i("SharePreferenceHelper",context.getClass().getCanonicalName());
//        if( context.getClass().getCanonicalName().equals("InstrumentActivity")){
            if(mSharedPreferences==null){
                mSharedPreferences = context.getSharedPreferences(Consts.PREFERENCE_600, MODE_PRIVATE);
                Log.i("SharePreferenceHelper","init");
            }
//        }
        return mSharedPreferences;
    }

    public static SharedPreferences getInstance(){
        return mSharedPreferences;
    }

    public static void writeBoolToSP(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void writeIntToSP(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void writeStringToSP(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
