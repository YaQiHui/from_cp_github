package com.color.measurement.from_cp.UI.Activitys.instrument_600.instrument;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.app;

/**
 * Created by wpc on 2016/12/22.
 */

public class ModificateDialog extends DialogFragment {

    private Context mContext;
    private LayoutInflater mInflater;
    private DialogInterface.OnClickListener positive, negative, meutral;
    private String name, tips;

    ModificateDialog(Context context, String name, String tips, DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative, DialogInterface.OnClickListener meutral) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.positive = positive;
        this.negative = negative;
        this.meutral = meutral;
        this.name = name;
        this.tips = tips;
    }

    EditText et1, et2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("修改");
        View view = mInflater.inflate(R.layout.motificatedialog, null);
        et1 = (EditText) view.findViewById(R.id.et1_name_modificate);
        et2 = (EditText) view.findViewById(R.id.et2_name_modificate);
        if(name!=null){
            et1.setText(name);
        }
        et1.addTextChangedListener(mTextWatcher);
       if(tips!=null){
           et2.setText(tips);
       }
        et2.addTextChangedListener(mTextWatcher2);
        builder.setView(view);
        builder.setNeutralButton("设为标样", meutral);
        builder.setPositiveButton("确认修改", positive);
        builder.setNegativeButton("删除样本", negative);
        return builder.create();
    }


    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            app.changed_name = null;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            app.changed_name = et1.getText().toString();
        }
    };
    private TextWatcher mTextWatcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            app.changed_tips = null;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            app.changed_tips = et2.getText().toString();
        }
    };
}
