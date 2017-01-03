package com.color.measurement.from_cp.UI.fragments.dialog_fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.color.measurement.from_cp.R;

import java.util.ArrayList;

/**
 * Created by wpc on 2016/12/6.
 */

public class SelectBLEDriveDialog extends DialogFragment implements DiscoverViewCallBack {


    Activity mContext;
    String title;

    View.OnClickListener positive, nagtive;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private ListView lv;
    private ProgressBar pb;
    private TextView tv;
    private Button bt1;
    private Button bt2;

    public SelectBLEDriveDialog(
            Activity context, String title,
            View.OnClickListener postive,
            View.OnClickListener nagtive,
            AdapterView.OnItemClickListener mOnItemClickListener
    ) {
        mContext = context;
        this.title = title;
        this.positive = postive;
        this.nagtive = nagtive;
        this.mOnItemClickListener = mOnItemClickListener;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        View v = LayoutInflater.from(mContext).inflate(R.layout.progress_message_list, null);
        tv = (TextView) v.findViewById(R.id.message_BLE_dialog);
        lv = (ListView) v.findViewById(R.id.lv_BLE_dialog);
        pb = (ProgressBar) v.findViewById(R.id.pb_BLE_dialog);

        bt1 = (Button) v.findViewById(R.id.discovery_BLE);
        bt2 = (Button)v.findViewById(R.id.cancel_BLE);
        bt1.setOnClickListener(positive);
        bt2.setOnClickListener(nagtive);

        lv.setOnItemClickListener(mOnItemClickListener);

        builder.setView(v);
        return builder.create();
    }


    @Override
    public void refeshListView(ArrayList<String> items) {
        lv.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items));
    }

    @Override
    public void setDescoveryState(final boolean isDiscovery) {
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isDiscovery) {
                    tv.setText("正在搜索");
                    bt1.setText("停止搜索");
                    pb.setVisibility(View.VISIBLE);

                } else {
                    tv.setText("搜索结束");
                    bt1.setText("搜索设备");
                    pb.setVisibility(View.GONE);
                }
            }
        });
    }


}
