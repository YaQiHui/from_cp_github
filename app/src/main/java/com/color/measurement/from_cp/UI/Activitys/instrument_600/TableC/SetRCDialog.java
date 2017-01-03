package com.color.measurement.from_cp.UI.Activitys.instrument_600.TableC;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.color.measurement.from_cp.Manager.SP.SharePreferenceHelper;
import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.others.interfaace.DismissCallback;

import java.util.ArrayList;

/**
 * Created by wpc on 2016/12/26.
 */

public class SetRCDialog extends DialogFragment {

    private Context mContext;
    private LayoutInflater mInflater;

    ArrayList<RCSet> sets;

    public SetRCDialog(Context context, ArrayList<String> titles
    ) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.sets = getsets(titles);
    }

    private ArrayList<RCSet> getsets(ArrayList<String> titles) {
        SharedPreferences sp = new SharePreferenceHelper().getInstance();
        ArrayList sets = new ArrayList<>();
        for (String str : titles) {
            sets.add(new RCSet(str, sp.getFloat(str, 2.0f)));
        }
        Log.i("getsets", sets.toString());
        return sets;
    }

    private DismissCallback mCallback;

    void setOnDismissCallBack(DismissCallback callback) {
        mCallback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("设置容差");
        ListView lv = new ListView(mContext);
        lv.setAdapter(new RCAdapter());
        builder.setView(lv);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp = SharePreferenceHelper.getInstance();
                SharedPreferences.Editor et = sp.edit();
                for (RCSet set : sets) {
                    et.putFloat(set.getName(), set.getRc());
                    et.commit();
                }
            }
        });
        builder.setNegativeButton("取消", null);

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mCallback.dismiss();
        Log.i("onDismiss", sets.toString());
    }

    class RCAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return sets.size();
        }

        @Override
        public Object getItem(int position) {
            return sets.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.setrcdialog, parent, false);
                vh = new ViewHolder();
                vh.add = (Button) convertView.findViewById(R.id.bt_add_item_rcset);
                vh.div = (Button) convertView.findViewById(R.id.bt_div_item_rcset);
                vh.et = (EditText) convertView.findViewById(R.id.et_item_rcset);
                vh.name = (TextView) convertView.findViewById(R.id.name_item_rcset);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            final ViewHolder finalVh = vh;
            vh.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float i = getSpeed(Float.valueOf(finalVh.et.getText().toString()),true);
                    finalVh.et.setText(sets.get(position).getRc() + i + "");
                    sets.get(position).setRc(sets.get(position).getRc() + i);
                }
            });
            vh.div.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float i = getSpeed(Float.valueOf(finalVh.et.getText().toString()),false);
                    finalVh.et.setText(sets.get(position).getRc() - i + "");
                    sets.get(position).setRc(sets.get(position).getRc() - i);
                }
            });
            vh.name.setText(sets.get(position).getName());
            vh.et.setText(sets.get(position).getRc() + "");
            return convertView;
        }

        class ViewHolder {
            TextView name;
            Button add, div;
            EditText et;
        }
    }

    private float getSpeed(Float aFloat, boolean add) {
        float i;
        if (aFloat <=0.11) {
            if (add) {
                i = 0.1f;
            } else {
                i = 0;
            }
        } else if (aFloat < 1) {
            i = 0.1f;
        } else if (aFloat < 5) {
            i = 0.5f;
        } else if (aFloat < 10) {
            i = 1f;
        } else if (aFloat <20) {
            i = 2f;
        } else {
            if(add){
                i = 0;
            }else {
                i=2f;
            }

        }
        return i;
    }
}
