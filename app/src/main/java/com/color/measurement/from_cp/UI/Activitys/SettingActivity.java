package com.color.measurement.from_cp.UI.Activitys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.color.measurement.from_cp.R;

import com.color.measurement.from_cp.bean.Consts;

public class SettingActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private AppCompatDelegate mDelegate;
    private Toolbar toolbar;

    private ListPreference zhaoming, guancha, tubiao, huadongzhou;
    private CheckBoxPreference yulankuang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_icons));
        toolbar.setTitle("设置");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addPreferencesFromResource(R.xml.preferences);

        zhaoming = (ListPreference) findPreference(Consts.KEY_ZHAOMING);
        guancha = (ListPreference) findPreference(Consts.KEY_GUANCHA);
        tubiao = (ListPreference) findPreference(Consts.KEY_TUBIAO);
        huadongzhou = (ListPreference) findPreference(Consts.KEY_CHART_PRE);
        yulankuang = (CheckBoxPreference) findPreference(Consts.KEY_PRE_COLOR);
    }


    private void setSupportActionBar(Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Setup the initial values
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        zhaoming.setSummary(sharedPreferences.getString(Consts.KEY_ZHAOMING, "环境光照条件"));
        guancha.setSummary(sharedPreferences.getString(Consts.KEY_GUANCHA, "观测角度"));
        tubiao.setSummary(sharedPreferences.getString(Consts.KEY_TUBIAO,"选择默认的图标样式"));
        huadongzhou.setSummary(sharedPreferences.getString(Consts.KEY_CHART_PRE,"设置预览图哪个方向可以滑动"));
        yulankuang.setSummary(sharedPreferences.getBoolean(Consts.KEY_PRE_COLOR,false)? "打开":"关闭");
        // Set up a listener whenever a key changes
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    switch(key){
        case Consts.KEY_ZHAOMING:
            String str1=sharedPreferences.getString(key,null);
            findPreference(key).setSummary(str1);
            Consts.str_zm_value=str1;
            break;
        case Consts.KEY_GUANCHA:
            String str2=sharedPreferences.getString(key,null);
            findPreference(key).setSummary(str2);
            Consts.str_gc_value=str2;
            break;
        case Consts.KEY_TUBIAO:
            String str3=sharedPreferences.getString(key,null);
            findPreference(key).setSummary(str3);
            switch (str3){
                case "线|点":
                    Consts.chart_hasLines=true;
                    Consts.chart_hasPoint=true;
                    break;
                case "点":
                    Consts.chart_hasPoint=true;
                    Consts.chart_hasLines=false;
                    break;
                case"线":
                    Consts.chart_hasLines=true;
                    Consts.chart_hasPoint=false;
                    break;
            }
            break;
        case Consts.KEY_CHART_PRE:
            findPreference(key).setSummary(sharedPreferences.getString(key,null));
            break;
        case Consts.KEY_PRE_COLOR:
            findPreference(key).setSummary(sharedPreferences.getBoolean(key,true)? "打开":"关闭");
            break;
        case Consts.KEY_EDIT:

            break;
        case Consts.KEY_SHARE:

            break;
        case Consts.KEY_FANKUI:

            break;
    }
    }
}
