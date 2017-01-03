package com.color.measurement.from_cp.Manager.Ble;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.color.measurement.from_cp.UI.Activitys.instrument_600.InstrumentActivity;
import com.color.measurement.from_cp.UI.fragments.dialog_fragment.SelectBLEDriveDialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * Created by wpc on 2016/12/7.
 */

public class BlueToothManager {


    private final String tag = "BlueToothManager";
    public static final String uuid = "0000ffe0-0000-1000-8000-00805f9b34fb";//00001101-0000-1000-8000-00805F9B34FB
    //0000ffe0-0000-1000-8000-00805f9b34fb


    private boolean isDiscovery = false;
    private Activity mContext;
    private static BlueToothManager instance;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket client;
    OutputStream mOutputStream;
    InputStream mInputStream;

    private SelectBLEDriveDialog mSelectBLEDriveDialog;
    private ArrayList<BluetoothDevice> deveicesDescoved;
    private BlueToothBroadcastReceiver blueToothBroadcastReceiver;

    private BlueToothManager(Activity context) {
        mContext = context;
    }

    public static BlueToothManager getInstance(@Nullable Activity context) {
        if (instance == null) {
            instance = new BlueToothManager(context);
        }
        return instance;
    }

    public void showFindDriveDialog() {
        registerReceiver(mContext);
        mSelectBLEDriveDialog = new SelectBLEDriveDialog(mContext, "搜索设备", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDiscovery) {
                    startDescovery();
                    mSelectBLEDriveDialog.setDescoveryState(true);
                    isDiscovery = true;
                } else {
                    stopDiscover();
                    mSelectBLEDriveDialog.setDescoveryState(false);
                    isDiscovery = false;
                }

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = deveicesDescoved.get(position);
                if (connect(device)) {
                    mSelectBLEDriveDialog.dismiss();
                    unregisterReceiver(mContext);
                    mContext.startActivity(new Intent(mContext, InstrumentActivity.class));
                }else {
                    Toast.makeText(mContext,"连接出错",Toast.LENGTH_SHORT).show();
                }

            }
        });

        mSelectBLEDriveDialog.show(mContext.getFragmentManager(), "selectBLEdialog");
    }

    boolean connect(BluetoothDevice device) {
        Log.i("connect",device.toString());
        UUID ud = UUID.fromString(uuid);
        try {
            client = device.createRfcommSocketToServiceRecord(ud);
            client.connect();
            mOutputStream = client.getOutputStream();
            mInputStream = client.getInputStream();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 给打印机 发送数据
    @SuppressLint("NewApi")
    private void sendBytes(byte[] mess) {
        if (client == null || !client.isConnected()) {
            Toast.makeText(mContext, "没有连接", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        try {
            OutputStream os = client.getOutputStream();
            os.write(mess);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeBT() throws IOException {
        try {

            mOutputStream.close();
            mInputStream.close();
            client.close();
            Log.i(tag, "Bluetooth Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean startDescovery() {
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        mBluetoothAdapter.startDiscovery();
        return true;
    }

    public void stopDiscover() {
        mBluetoothAdapter.cancelDiscovery();
    }

    //已连接过的列表
    public ArrayList<BluetoothDevice> getDevices() {
        ArrayList<BluetoothDevice> boundedlist = new ArrayList<>();
        if (mBluetoothAdapter != null) {
            Set<BluetoothDevice> Devices = mBluetoothAdapter.getBondedDevices();
            if (Devices.size() != 0) {
                Log.i("getDevices_size", Devices.size() + "");
                Iterator<BluetoothDevice> iterator = Devices.iterator();
                while (iterator.hasNext()) {
                    BluetoothDevice device = iterator.next();
                    boundedlist.add(device);
                }
            }
        } else {
            Log.i(tag, "madapter为空");
            return null;
        }
        return boundedlist;
    }


    private ArrayList<String> getDrivesInfo(ArrayList<BluetoothDevice> bluetoothDevices) {
        ArrayList<String> drives = new ArrayList<>();
        for (BluetoothDevice drive : bluetoothDevices) {
            drives.add(drive.getName() + drive.getAddress());
        }
        return drives;
    }


    //注册接受搜索结果的广播
    public boolean registerReceiver(Context context) {
        blueToothBroadcastReceiver = new BlueToothBroadcastReceiver();
        IntentFilter startFilter = new IntentFilter(
                BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        IntentFilter foundFilter = new IntentFilter(
                BluetoothDevice.ACTION_FOUND);
        IntentFilter endFilter = new IntentFilter(
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        IntentFilter changeFilter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        context.registerReceiver(blueToothBroadcastReceiver, startFilter);
        context.registerReceiver(blueToothBroadcastReceiver, foundFilter);
        context.registerReceiver(blueToothBroadcastReceiver, endFilter);
//        mContext.registerReceiver(blueToothBroadcastReceiver, changeFilter);
//        IntentFilter startFilter = new IntentFilter(
//                BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//        IntentFilter foundFilter = new IntentFilter(
//                BluetoothDevice.ACTION_FOUND);
//        IntentFilter endFilter = new IntentFilter(
//                BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        mContext.registerReceiver(blueToothBroadcastReceiver, startFilter);
//        mContext.registerReceiver(blueToothBroadcastReceiver, foundFilter);
//        mContext.registerReceiver(blueToothBroadcastReceiver, endFilter);
        Log.i("Receicer", "register");
        return true;
    }

    //记得unregister
    public boolean unregisterReceiver(Context context) {
        context.unregisterReceiver(blueToothBroadcastReceiver);
        Log.i("Receicer", "unregister");
        return true;
    }

    class BlueToothBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("action", action);
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                deveicesDescoved = new ArrayList<>();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i("action found", device.getName() + device.getAddress());
                if (!(deveicesDescoved.contains(device))) {
                    deveicesDescoved.add(device);
                    mSelectBLEDriveDialog.refeshListView(getDrivesInfo(deveicesDescoved));
                    Log.i("add deveice" + device.getName(), deveicesDescoved.size() + "");
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
            }
        }

    }

}
