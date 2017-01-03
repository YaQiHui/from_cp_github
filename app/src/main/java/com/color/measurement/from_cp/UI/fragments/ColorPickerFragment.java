package com.color.measurement.from_cp.UI.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.Activitys.Main.MainActivity;
import com.color.measurement.from_cp.UI.app;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;


/**
 * Created by wpc on 2016/9/12.
 */
public class ColorPickerFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_colorpicker_layout, null);

        final ColorPicker
                picker = (ColorPicker) view.findViewById(R.id.picker);
        SVBar svBar = (SVBar) view.findViewById(R.id.svbar);
        OpacityBar opacityBar = (OpacityBar) view.findViewById(R.id.opacitybar);
        SaturationBar saturationBar = (SaturationBar) view.findViewById(R.id.saturationbar);
        ValueBar valueBar = (ValueBar) view.findViewById(R.id.valuebar);

        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);

//To get the color
//        Log.e("colorpicker", picker.getColor() + "");

//To set the old selected color u can do it like this
        picker.setOldCenterColor(app.oldcolor);
// adds listener to the colorpicker which is implemented
//in the activity
        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
//                Log.e("colors", picker.getColor() + "");
            }
        });

//to turn of showing the old color
        picker.setShowOldCenterColor(true);

//        picker.setOnColorSelectedListener(new ColorPicker.OnColorSelectedListener() {
//            @Override
//            public void onColorSelected(int color) {
//                tv.setText(picker.getColor());
//            }
//        });

//adding onChangeListeners to bars
//        opacityBar.setOnOpacityChangedListener(new OpacityBar.OnOpacityChangedListener() {
//            @Override
//            public void onOpacityChanged(int opacity) {
//
//            }
//        });
//
//        saturationBar.setOnSaturationChangedListener(new SaturationBar.OnSaturationChangedListener() {
//            @Override
//            public void onSaturationChanged(int saturation) {
//
//            }
//        });

        builder.setCancelable(false);
        builder.setTitle("ColorPicker").setView(view).
                setNegativeButton("no", null).
                setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        app.color = picker.getColor();
                    }
                });
//                .setNeutralButton("chose a midcolor", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        try {
//                            Field field = instance.getClass()
//                                    .getSuperclass().getDeclaredField(
//                                            "mShowing");
//                            field.setAccessible(true);
//                            //   将mShowing变量设为false，表示对话框已关闭
//                            field.set(instance, false);
//                            instance.dismiss();
//
//                        } catch (Exception e) {
//
//                        }
//
//                    }
//                });
        return builder.create();
    }

    @Override
    public void onDestroy() {
        int color = app.color;
        String str = Integer.toHexString(color);
        ((MainActivity) getActivity()).refreshButton(color, "#" + str);
        super.onDestroy();
    }
}
