package com.color.measurement.from_cp.UI.Activitys.instrument_600.TableC;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.color.measurement.from_cp.R;

/**
 * Created by wpc on 2016/12/19.
 */

public class AddTitleDialog extends DialogFragment {

    private Context mContext;
    private LayoutInflater mInflater;


    DialogInterface.OnClickListener positive;

    public static final String[] type = {"L*a*b*", "XYZ", "L*C*h", "L*u*v*", "Yxy"};
    public static final int[] type_id = {R.array.Lab, R.array.XYZ, R.array.LCh, R.array.Luv, R.array.Yxy};
    public static final String[] method_name = {"XYZtoHunterLab", "XYZtoXYZ", "XYZtoCIE_LabCH", "XYZtoLuv", "XYZtoYxy"};

    public boolean[] check_state = new boolean[type.length];


    public AddTitleDialog(Context context, boolean[] check_state, DialogInterface.OnClickListener positive) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.positive = positive;
        this.check_state = check_state;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("添加条目");

        ListView listView = new ListView(mContext);
        listView.setPadding(40, 10, 40, 10);
        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
        Adapter adapter = new Adapter();
        listView.setAdapter(adapter);
        builder.setView(listView);
        builder.setPositiveButton("确定", positive);
        builder.setNeutralButton("取消", null);

        return builder.create();
    }

    class Adapter extends BaseAdapter {


        @Override
        public int getCount() {
            return type.length;
        }

        @Override
        public Object getItem(int position) {
            return type[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            CheckBox cb = new CheckBox(mContext);
            cb.setChecked(check_state[position]);
            cb.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        check_state[position] = true;
                    } else {
                        check_state[position] = false;
                    }
                }
            });
            cb.setText(type[position]);
            return cb;
        }
    }

    public boolean[] getCheck_state() {
        return check_state;
    }

    public void setCheck_state(boolean[] check_state) {
        this.check_state = check_state;
    }
}
