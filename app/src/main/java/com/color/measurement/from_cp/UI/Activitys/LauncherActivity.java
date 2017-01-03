package com.color.measurement.from_cp.UI.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.color.measurement.from_cp.R;

import com.color.measurement.from_cp.UI.Activitys.Main.MainActivity;
import com.color.measurement.from_cp.Manager.SP.MySharedPreferences;

public class LauncherActivity extends AppCompatActivity {

    private MySharedPreferences  mMySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        loadData();

    }
    void loadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                intent = new Intent(LauncherActivity.this, MainActivity.class);

                if (MySharedPreferences.getInstance()
                        .isConnectionInstrument()) {
                    //如果连接过就去跳转到仪器页面 否则进入颜色空间转换页面
                    intent.putExtra("lianjieyiqi",true);
                }
                startActivity(intent);
                finish();
            }
        }, 1500);
        mMySharedPreferences = MySharedPreferences.getInstance();
        mMySharedPreferences.setUserInfo(getSharedPreferences(MySharedPreferences.USER_INFO_FILE, Context.MODE_PRIVATE));
        mMySharedPreferences.setSettingInfo(getSharedPreferences(MySharedPreferences.SETTING_INFO, Context.MODE_PRIVATE));
    }
}
