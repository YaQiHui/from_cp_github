package com.color.measurement.from_cp.Utils;

import android.util.Log;

/**
 * Created by wpc on 2016/9/17.
 */
public class log {
    private static final boolean isdebug=true;

    void e(String str){
        if(isdebug){
            Log.e(getClass().getCanonicalName(),str);
        }
    }
    void i(String str){
        if(isdebug){
            Log.i(getClass().getCanonicalName(),str);
        }
    }
    void v(String str){
        if(isdebug){
            Log.v(getClass().getCanonicalName(),str);
        }
    }
    void d(String str){
        if(isdebug){
            Log.d(getClass().getCanonicalName(),str);
        }
    }
    void w(String str){
        if(isdebug){
            Log.w(getClass().getCanonicalName(),str);
        }
    }
}
