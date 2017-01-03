package com.color.measurement.from_cp.UI.Activitys.instrument_600;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.color.measurement.from_cp.Manager.BLE_4.BlueToothManagerForBLE;
import com.color.measurement.from_cp.Manager.SP.SharePreferenceHelper;
import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.instrument.Instrument;
import com.color.measurement.from_cp.others.adapter.FragmentViewPagerAdapter;

public class InstrumentActivity extends AppCompatActivity {

    TabLayout tl;
    ViewPager vp;
    FragmentViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharePreferenceHelper.init(this);

        setContentView(R.layout.activity_instrument);
        vp = (ViewPager) findViewById(R.id.vp_instrument);
        mAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        mAdapter.addItem(new Instrument(), "仪器");
        mAdapter.addItem(new DB(), "记录");
        vp.setAdapter(mAdapter);
        tl = (TabLayout) findViewById(R.id.tl_instrument);
        tl.setTabMode(TabLayout.MODE_FIXED);
        tl.setupWithViewPager(vp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.instrument_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.select_op_instrument:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        BlueToothManagerForBLE.getInstance(null).disconect();
        super.onDestroy();
    }
}
