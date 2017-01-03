package com.color.measurement.from_cp.UI.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.color.measurement.from_cp.R;

import java.util.Set;

/**
 * Created by wpc on 2016/9/18.
 */
public class BlueToothFragment extends DialogFragment {


    private BluetoothAdapter adapter;
    private Context context = getActivity();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_bluetooth, null);

        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            // 设备不支持蓝牙
        }
// 打开蓝牙
        if (!adapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // 设置蓝牙可见性，最多300秒
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            context.startActivity(intent);
        }


        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        for(int i=0; i<devices.size(); i++)
        {
            BluetoothDevice device = (BluetoothDevice) devices.iterator().next();
            System.out.println(device.getName());
        }



        return builder.create();
    }
}
