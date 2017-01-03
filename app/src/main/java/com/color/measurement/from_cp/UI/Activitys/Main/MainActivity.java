package com.color.measurement.from_cp.UI.Activitys.Main;

import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.color.measurement.from_cp.Manager.BLE_4.BlueToothManagerForBLE;
import com.color.measurement.from_cp.Manager.Ble.BlueToothManager;
import com.color.measurement.from_cp.Manager.SP.SharePreferenceHelper;
import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.Activitys.AboutUsActivity;
import com.color.measurement.from_cp.UI.Activitys.BaseAppCompatActivity;
import com.color.measurement.from_cp.UI.Activitys.FindActivity;
import com.color.measurement.from_cp.UI.Activitys.SettingActivity;
import com.color.measurement.from_cp.UI.fragments.FirstFragment;
import com.color.measurement.from_cp.UI.fragments.SecondFragment;
import com.color.measurement.from_cp.UI.fragments.ThirdFragment;

public class MainActivity extends BaseAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainView {
    private int fragmentTag = 1;
    DrawerLayout drawer;
    private BlueToothManagerForBLE mBlueToothManagerForBLE;
    private BlueToothManager mBlueToothManager;

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", "onResume");
        mBlueToothManagerForBLE = BlueToothManagerForBLE.getInstance(MainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharePreferenceHelper.init(this);
//        NotificationUtils.notificationBasic(this);
//        xgs_notification();
        replace(new FirstFragment(), "first");
        Intent it = getIntent();
        if (it.getBooleanExtra("lianjieyiqi", false)) {
            replace(new ThirdFragment(), "third");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void replace(android.app.Fragment fragment, String tag) {
        getFragmentManager().beginTransaction().replace(R.id.fl_main, fragment, tag).commit();
    }


    void xgs_notification() {
        Notification.Builder builder = new Notification.Builder(this).setSmallIcon(R.drawable.ic_notifications_black_24dp).setPriority(Notification.PRIORITY_DEFAULT).setCategory(Notification.CATEGORY_MESSAGE).setContentTitle("Headsup Notification").setContentText("i am a Headsup notification.");
        Intent push = new Intent();
        push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        push.setClass(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, push, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentText("Heads-Up Notification on Android 5.0").setFullScreenIntent(pendingIntent, true);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1, builder.build());

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_connectBL:
//                startActivity(new Intent(MainActivity.this, InstrumentActivity.class));
                mBlueToothManagerForBLE.showFindDriveDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            replace(new FirstFragment(), "first");
            fragmentTag = 1;
            setTitle("颜色转换");
        } else if (id == R.id.nav_gallery) {
            replace(new SecondFragment(), "second");
            fragmentTag = 2;
            setTitle("仪器");
        } else if (id == R.id.nav_slideshow) {
            replace(new ThirdFragment(), "third");
            fragmentTag = 3;
            setTitle("商城");
        } else {
            if (id == R.id.nav_manage) {
                Intent it = new Intent(MainActivity.this, FindActivity.class);
                startActivity(it);
            } else {
                if (id == R.id.nav_share) {
//                    ShareSDK.initSDK(this);
//                    OnekeyShare oks = new OnekeyShare();
////关闭sso授权
//                    oks.disableSSOWhenAuthorize();
//
//// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
//                    oks.setTitle("标题");
//// titleUrl是标题的网络链接，QQ和QQ空间等使用
//                    oks.setTitleUrl("http://sharesdk.cn");
//// text是分享文本，所有平台都需要这个字段
//                    oks.setText("我是分享文本");
//// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
////oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//// url仅在微信（包括好友和朋友圈）中使用
//                    oks.setUrl("http://sharesdk.cn");
//// comment是我对这条分享的评论，仅在人人网和QQ空间使用
//                    oks.setComment("我是测试评论文本");
//// site是分享此内容的网站名称，仅在QQ空间使用
//                    oks.setSite(getString(R.string.app_name));
//// siteUrl是分享此内容的网站地址，仅在QQ空间使用
//                    oks.setSiteUrl("http://sharesdk.cn");
//
//// 启动分享GUI
//                    oks.show(this);

                } else if (id == R.id.nav_send) {
                    startActivity(new Intent(MainActivity.this, AboutUsActivity.class));

                } else if (id == R.id.action_settings) {
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                }
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //back键退出检测
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (drawer.isDrawerOpen(GravityCompat.START)) {
                onBackPressed();
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序",
                            Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
            }

            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void refreshButton(int color, String text) {
        FragmentManager fm = getFragmentManager();
        FirstFragment fragment = (FirstFragment) fm.findFragmentByTag("first");
        fragment.setButtonColor(color, text);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 1:
//                if (resultCode == Activity.RESULT_OK) {
//                    mBlueToothManager.showFindDriveDialog();
//                    Toast.makeText(this, "蓝牙已启用", Toast.LENGTH_SHORT).show();
//                } else {
//                    mBlueToothManager.dismissDialog();
//                    Toast.makeText(this, "蓝牙未启用", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }
}

