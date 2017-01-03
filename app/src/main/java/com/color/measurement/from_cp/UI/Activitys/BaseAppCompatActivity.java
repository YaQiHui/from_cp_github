package com.color.measurement.from_cp.UI.Activitys;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wpc on 2016/9/14.
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    private static final boolean debug = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (debug) {
            Log.e(getClass().getCanonicalName(), "onCreate");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (debug) {
            Log.e(getClass().getCanonicalName(), "onStart");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (debug) {
            Log.e(getClass().getCanonicalName(), "onStop");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (debug) {
            Log.e(getClass().getCanonicalName(), "onDestroy");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (debug) {
            Log.e(getClass().getCanonicalName(), "onResume");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (debug) {
            Log.e(getClass().getCanonicalName(), "onPause");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (debug) {
            Log.e(getClass().getCanonicalName(), "onCreate");

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (debug) {
            Log.e(getClass().getCanonicalName(), "onRestart");
        }
    }

    public void Toast(CharSequence str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
}
