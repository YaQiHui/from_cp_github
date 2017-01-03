package com.color.measurement.from_cp.UI.fragments.child_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.color.measurement.from_cp.R;

/**
 * Created by wpc on 2016/8/27.
 */
public class Fourth_2_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fourth_1,container,false);
        return view;
    }
}
