package com.color.measurement.from_cp.UI.fragments.dialog_fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by wpc on 2016/12/6.
 */

public class ListDialogWithThreeOptions extends DialogFragment {


    private ListView lv;
    Context mContext;
    String title, message, fistText, secondText, thirdText;
    private DialogInterface.OnClickListener neutral_listener;
    private DialogInterface.OnClickListener positive_listener;
    private DialogInterface.OnClickListener nagtive_listener;

    public ListDialogWithThreeOptions(Context context, String title, String message,
                                      String firstText, DialogInterface.OnClickListener neutral_listener,
                                      String secondText, DialogInterface.OnClickListener positive_listener,
                                      String thirdText, DialogInterface.OnClickListener nagtive_listener
    ) {
        mContext = context;

        this.title = title;
        this.message = message;
        this.fistText = firstText;
        this.secondText = secondText;
        this.thirdText = thirdText;
        this.neutral_listener = neutral_listener;
        this.positive_listener = positive_listener;
        this.nagtive_listener = nagtive_listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);

        builder.setMessage(message);
        lv = new ListView(mContext);
        lv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        builder.setView(lv);

        builder.setNeutralButton(fistText, neutral_listener);
        builder.setPositiveButton(secondText, positive_listener);
        builder.setNeutralButton(thirdText, nagtive_listener);

        return builder.create();
    }
}
