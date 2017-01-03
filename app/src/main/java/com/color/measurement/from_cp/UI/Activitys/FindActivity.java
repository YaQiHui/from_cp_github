package com.color.measurement.from_cp.UI.Activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.color.measurement.from_cp.R;

import java.util.ArrayList;
import java.util.List;

import com.color.measurement.from_cp.others.adapter.FindTabAdapter;
import com.color.measurement.from_cp.UI.fragments.child_fragments.Fourth_1_Fragment;

public class FindActivity extends AppCompatActivity {


    private TabLayout tl;
    private ViewPager vp;
    private FragmentPagerAdapter fpa;
    private List<Fragment> fragments;
    private List<String> titles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);
        initData();
        initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("发现");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    void initView() {

        tl = (TabLayout) findViewById(R.id.tl_fourth);
        vp = (ViewPager) findViewById(R.id.vp_fourth);

        tl.setTabMode(TabLayout.MODE_FIXED);

        tl.addTab(tl.newTab().setText(titles.get(0)));
        tl.addTab(tl.newTab().setText(titles.get(1)));
        tl.addTab(tl.newTab().setText(titles.get(2)));

//        .setIcon(R.mipmap.ic_launcher));
//        app:tabMode="scrollable"
        fpa = new FindTabAdapter(getSupportFragmentManager(), fragments, titles);
        vp.setAdapter(fpa);
        tl.setupWithViewPager(vp);
    }

    void initData() {
        fragments = new ArrayList<>();
        fragments.add(Fourth_1_Fragment.createInstance(20));
        fragments.add(Fourth_1_Fragment.createInstance(20));
        fragments.add(Fourth_1_Fragment.createInstance(20));

        titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("收藏");
        titles.add("热文");
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
