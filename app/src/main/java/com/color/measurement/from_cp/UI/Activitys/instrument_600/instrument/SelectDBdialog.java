package com.color.measurement.from_cp.UI.Activitys.instrument_600.instrument;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;

import com.color.measurement.from_cp.Manager.SP.Consts;
import com.color.measurement.from_cp.Manager.SP.SharePreferenceHelper;
import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.app;
import com.color.measurement.from_cp.others.interfaace.DismissCallback;

import java.util.ArrayList;

import com.color.measurement.from_cp.Utils.T;

/**
 * Created by wpc on 2016/12/24.
 */

public class SelectDBdialog extends DialogFragment {

    private Context mContext;
    LayoutInflater mInflater;
    ArrayList<String> names;
    DismissCallback dismiss;

    SelectDBdialog(Context context, ArrayList<String> names, DismissCallback dismiss) {
        mContext = context;
        this.names = names;
        this.dismiss = dismiss;
        mInflater = LayoutInflater.from(mContext);
    }
    int i ;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("选择数据库");
        View view = mInflater.inflate(R.layout.select_db_dialog, null);

        GridView gv = (GridView) view.findViewById(R.id.gv_select_db_dialog);
        CheckBox cb = (CheckBox) view.findViewById(R.id.cb_named_atm);
        final EditText et = (EditText) view.findViewById(R.id.et_new_name_select_db);

        cb.setChecked(app.check_atm_name);
        if (app.check_atm_name) {
            i = SharePreferenceHelper.getInstance().getInt(Consts.TARGET_MAX_VALUE, 0);
            et.setText("target" + i);
            et.setSelectAllOnFocus(true);

        }
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                app.check_atm_name = isChecked;
            }
        });

        gv.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, names));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                app.table_name = names.get(position);
                SelectDBdialog.this.dismiss();
                dismiss.dismiss();
            }
        });


        Button bt = (Button) view.findViewById(R.id.bt_qd_select_db);
        Button bt2 = (Button) view.findViewById(R.id.bt_qx_select_db);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                String str = et.getText().toString();
                if (str == null | str.equals("")) {
                    T.show(mContext, "表名不能为空");
                } else {
                    app.table_name = str;
//                    insertConfigAndCreateTab(str);
                    dismiss();
                    dismiss.dismiss();
                }

                SharePreferenceHelper.writeIntToSP(Consts.TARGET_MAX_VALUE, i + 1);
//                    }
//                }).start();

            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        return builder.create();
    }
}
