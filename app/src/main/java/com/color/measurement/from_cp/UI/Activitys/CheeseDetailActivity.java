/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.color.measurement.from_cp.UI.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.color.measurement.from_cp.R;

public class CheeseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";

    private Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        getWindow().setEnterTransition(new Explode());//拼接
//        getWindow().setEnterTransition(new Slide());//上滑
        getWindow().setEnterTransition(new Fade());//渐入渐出
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        loadBackdrop();
    }

    private void loadBackdrop() {
        imageView = (ImageView) findViewById(R.id.backdrop);
        imageView.setImageResource(R.drawable.cheese_1);
        //        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}
