package com.color.measurement.from_cp.UI.Activitys.instrument_600.TableC;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.app;
import com.color.measurement.from_cp.others.interfaace.DismissCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.color.measurement.from_cp.Utils.Math.ArrayUtil;

/**
 * Created by wpc on 2016/12/20.
 */

public class ResultFragment extends Fragment {

    Activity mContext;
    ListView lv;
    AdapterForLABResult adapter;
    ArrayList<String> titles;
    String[] con_index = new String[]{"by", "sy", "rc", "wc"};
//    HashMap<String, HashMap<String, Double>> table_data;


    List<Double> stand_lab;
    List<Double> test_lab;

    //每次刷新都是replace  数据在缓存在上层TabSwitcher
    public ResultFragment(Activity context, ArrayList<String> titles, @Nullable List stand_lab, @Nullable List test_lab) {
        mContext = context;
        if (titles != null) this.titles = titles;
        if (stand_lab != null) this.stand_lab = stand_lab;
        if (test_lab != null) this.test_lab = test_lab;
    }

    AddTitleDialog add;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.instrument_result_table, container, false);
        v.findViewById(R.id.add_titles_instrument).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add == null) {
                    add = new AddTitleDialog(mContext, app.check_state, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i("choseTitles", Arrays.toString(app.check_state));
                            app.check_state = add.getCheck_state();
                            initTitles();
                        }
                    });
                }
                add.show(mContext.getFragmentManager(), "addTitleDialog");
            }
        });
        v.findViewById(R.id.bt_rc_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SetRCDialog d = new SetRCDialog(mContext,titles);
                d.show(mContext.getFragmentManager(), "setRCdialog");
                d.setOnDismissCallBack(new DismissCallback() {
                    @Override
                    public void dismiss() {
                        refeshTable();
                    }
                });
            }
        });
        lv = (ListView) v.findViewById(R.id.lv_instrument);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        initTitles();
        return v;
    }

    private void initTitles() {
        titles = new ArrayList<>();
        for (int i = 0; i < app.check_state.length; i++) {
            if (app.check_state[i]) {
                ArrayUtil.addStringsToStringArray(titles, mContext.getResources().getStringArray(AddTitleDialog.type_id[i]));
            }
        }
        Log.i("titles", titles.toString());
        refeshTable();
    }


    private void refeshTable() {
        adapter = new AdapterForLABResult(mContext, titles, stand_lab, test_lab);
        lv.setAdapter(adapter);
    }
}
