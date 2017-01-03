package com.color.measurement.from_cp.UI.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.ListView;

import com.color.measurement.from_cp.R;

/**
 * Created by wpc on 2016/12/15.
 */

public class FindBlueToothActivity extends Activity {

    FindBlueToothActivity (){

    }

    ListView lv;
    Button bt;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.findbluetooth);
    }
}
