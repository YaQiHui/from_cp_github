package com.color.measurement.from_cp.Manager.BLE_4;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.InstrumentActivity;
import com.color.measurement.from_cp.UI.app;
import com.color.measurement.from_cp.UI.fragments.dialog_fragment.SelectBLEDriveDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.color.measurement.from_cp.Utils.Math.clsPublic;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by wpc on 2016/12/6.
 */

public class BlueToothManagerForBLE {
    public static final String INIT_SUCCESS = "com.color.measurement.init_success";

    public static final String RECEIVE_WRONG_DATA = "com.color.measurement.receive_wrong_data";
    public static final String RECEIVE_TEST_DATA = "com.color.measurement.receive_test_data";

    public static final String WHITE_TEXT_SUCCESS = "com.color.measurement.white_test_success";
    public static final String WHITE_TEXT_FAILD = "com.color.measurement.white_test_faild";
    public static final String BLACK_TEXT_SUCCESS = "com.color.measurement.black_test_success";
    public static final String BLACK_TEXT_FAILD = "com.color.measurement.black_test_faild";
//    public static final String GET_DATA_RECEIVER = "com.color.measurement.getdatareceiver";
//    public static final String GET_DATA_RECEIVER = "com.color.measurement.getdatareceiver";
//    public static final String GET_DATA_RECEIVER = "com.color.measurement.getdatareceiver";


    private static Activity mContext;
    private static BlueToothManagerForBLE instance;
    private final String[] address = {"", ""};
    private final String TAG = "BlueToothManager";
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private static final int REQUEST_ENABLE_BT = 1;

    private boolean isDiscovering;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<BluetoothDevice> mBluetoothDevices;
    private BluetoothDevice mSelectedDevices;
    private BluetoothGatt mBluetoothGatt;
    private SelectBLEDriveDialog mSelectBLEDriveDialog;
    private Handler mHandler;

    private ServiceConnection mServiceConnection;
    BluetoothLeService mBluetoothLeService;

    private boolean mConnected = false;

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.connected);
                Log.i("onReceive", "ACTION_GATT_CONNECTED");
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                mContext.finish();
                Log.i("onReceive", "ACTION_GATT_DISCONNECTED");
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
                Log.i("onReceive", "ACTION_GATT_SERVICES_DISCOVERED");
                mContext.sendBroadcast(new Intent(INIT_SUCCESS));
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                Log.i("onReceive", "ACTION_DATA_AVAILABLE");
            }
        }
    };

    public static BlueToothManagerForBLE getInstance(@Nullable Activity context) {
        if (instance == null) {
            instance = new BlueToothManagerForBLE(context);
        }
        if (context != null) {
            mContext = context;
        }
        return instance;
    }


    public void regeisterReceiver(Context context) {
        context.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(app.BLE_drive_address);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    public void unRegeisterReceiver(Context context) {
        context.unregisterReceiver(mGattUpdateReceiver);
    }

    public void bindService(Context context) {
        if (mServiceConnection == null) {
            mServiceConnection = new ServiceConnection() {

                @Override
                public void onServiceConnected(ComponentName componentName, IBinder service) {
                    mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
                    if (!mBluetoothLeService.initialize()) {
                        Log.e(TAG, "Unable to initialize Bluetooth");
                        mContext.finish();
                    }
                    // Automatically connects to the device upon successful start-up initialization.
                    if (mBluetoothLeService.connect(mSelectedDevices.getAddress())) {
                        Log.i("onServiceConnected", "connect");
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    mBluetoothLeService = null;
                }
            };
        }
        Intent gattServiceIntent = new Intent(context, BluetoothLeService.class);
        context.bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    public void unBindService(Context context) {
        context.unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }


    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }


//    private static IntentFilter GetDataIntentFilter() {
//        final IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(RECEIVE_TEST_DATA);
//
//        intentFilter.addAction(BLACK_TEXT_SUCCESS);
//        intentFilter.addAction(BLACK_TEXT_FAILD);
//        intentFilter.addAction(WHITE_TEXT_SUCCESS);
//        intentFilter.addAction(WHITE_TEXT_FAILD);
//
//        return intentFilter;
//    }

    private void updateConnectionState(final int resourceId) {
        Log.i("updataConnectionState", mContext.getResources().getString(resourceId));
    }

    private void displayData(String data) {
        if (data != null) {
            Log.i("displayData", data);
//            mDataField.setText(data);
        }
    }

    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = mContext.getResources().getString(R.string.unknown_service);
        String unknownCharaString = mContext.getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            Log.i("gattService", gattService.toString());
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            Log.i("uuid", uuid);
            currentServiceData.put(LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                Log.i("gattCharacteristic", gattCharacteristic.toString());
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mBluetoothDevices == null) {
                                mBluetoothDevices = new ArrayList<BluetoothDevice>();
                            }
                            if (!mBluetoothDevices.contains(device)) {
                                mBluetoothDevices.add(device);
                                Log.i("LeScanCallback", device.getName() + device.toString());
                            }
                            mSelectBLEDriveDialog.refeshListView(getDrivesInfo(mBluetoothDevices));
                        }
                    });
                }
            };

    private BlueToothManagerForBLE(Activity context) {
        mContext = context;
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(context, "设备不支持 no BluetoothManager found", Toast.LENGTH_SHORT).show();
        } else {
            BluetoothManager mBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = mBluetoothManager.getAdapter();
            if (mBluetoothAdapter == null) {
                Toast.makeText(context, "设备不支持 no BluetoothAdapter  found", Toast.LENGTH_SHORT).show();
            }
            mHandler = new Handler();
            mBluetoothDevices = new ArrayList<>();
        }
    }
    //搜索蓝牙设备相关方法

    private ArrayList<String> getDrivesInfo(ArrayList<BluetoothDevice> bluetoothDevices) {
        ArrayList<String> drives = new ArrayList<>();
        for (BluetoothDevice drive : bluetoothDevices) {
            drives.add(drive.getName() + drive.getAddress());
        }
        return drives;
    }

    public void openBlueTooth() {
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mContext.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    boolean isShowing = false;

    public void showFindDriveDialog() {
        isDiscovering = false;
        regeisterReceiver(mContext);
        if (mSelectBLEDriveDialog == null) {
            mSelectBLEDriveDialog = new SelectBLEDriveDialog(mContext, "选择设备",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isDiscovering) {
                                scanLeDevice(false);
                                mSelectBLEDriveDialog.setDescoveryState(false);
                            } else {
                                if (mBluetoothAdapter.isEnabled()) {
                                    scanLeDevice(true);
                                    mSelectBLEDriveDialog.setDescoveryState(true);
                                } else {
                                    openBlueTooth();
                                }
                            }
                        }
                    },
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSelectBLEDriveDialog.dismiss();
                        }
                    },
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            mSelectedDevices = mBluetoothDevices.get(position);
                            if (mSelectedDevices.getName().contains("HeMe")) {
                                final Intent intent = new Intent(mContext, InstrumentActivity.class);
                                if (isDiscovering) {
                                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                                    isDiscovering = false;
                                }
                                mSelectBLEDriveDialog.dismiss();
                                unRegeisterReceiver(mContext);
                                mContext.startActivity(intent);
                            } else {
                                Toast.makeText(mContext, "无法识别该设备", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        mSelectBLEDriveDialog.show(mContext.getFragmentManager(), "listdialog_BLE");
    }

    public void dismissDialog() {
        if (mSelectBLEDriveDialog != null) {
            mSelectBLEDriveDialog.dismiss();
            isShowing = false;
        }
    }

    private void scanLeDevice(final boolean enable) {
        isDiscovering = enable;
        if (enable) {
            if (mBluetoothDevices.size() != 0) {
                mBluetoothDevices.clear();
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mSelectBLEDriveDialog.setDescoveryState(false);
                }
            }, 30000);
            mBluetoothAdapter.startLeScan(mLeScanCallback);
            mSelectBLEDriveDialog.setDescoveryState(true);
        } else {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mSelectBLEDriveDialog.setDescoveryState(false);
        }
    }

    public void connectToDevice() {
        Log.i("connectToDevice", "Connecting to the device NAME: " + mSelectedDevices.getName() + " HWADDR: " + mSelectedDevices.getAddress());
        mBluetoothGatt = mSelectedDevices.connectGatt(mContext, true, mGattCallback);
    }

    private BluetoothGattService mBTService = null;
    private BluetoothGattCharacteristic mBTValueCharacteristic = null;
    final static private UUID unknownservice = BleDefinedUUIDs.Service.UNKNOWN_SERVICE;
    final static private UUID unknowncharacteristic = BleDefinedUUIDs.Characteristic.UNKNOWN_CHARACTERISTIC;
    /* callbacks called for any action on HR Device */


    int index;
    byte[] result;

    TestResultData testResultData;

    public TestResultData getTestResultData() {
        return testResultData;
    }

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {//连接成功
//                log("Device connected");
                mBluetoothGatt.discoverServices();//连接成功后就去找出该设备中的服务
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {//连接失败
//                log("Device disconnected");
            }
        }

        @Override//当设备是否找到服务时，会回调该函数
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {//找到服务了
                Log.i("gatt_service", "Services discovered" + gatt.toString());
                //在这里可以对服务进行解析，寻找到你需要的服务
                mBTService = mBluetoothGatt.getService(unknownservice);
                if (mBTService == null) {
                    Log.i("getUnknownService", "Could not get Heart Rate Service");
                } else {
                    Log.i("getUnknownService", "Heart Rate Service successfully retrieved");
                    getHrCharacteristic();
                }
            } else {
//                log("Unable to discover services");
            }
        }


        @Override//当向设备Descriptor中写数据时，会回调该函数
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override//设备发出通知时会调用到该接口
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            Log.i("onCharacteristicChanged", clsPublic.bytesToHexString(characteristic.getValue()));
            byte[] item = characteristic.getValue();
            if (item.length == 1) {
//                T.show(mContext,"返回数据长度有误");
                Log.i("testResultData", "返回数据长度有误");
                mContext.sendBroadcast(new Intent(RECEIVE_WRONG_DATA));
            }
            else if (item.length == 5 ) {
                if (item[1] == 0x02) {
                    if (item[2] == 0x00) {
                        //黑校准成功
                        Log.i("sendBroadcast","BLACK_TEXT_SUCCESS");
                        mContext.sendBroadcast(new Intent(BLACK_TEXT_SUCCESS));
                    } else if (item[2] == 0x01) {
                        //黑校准失败
                        Log.i("sendBroadcast","BLACK_TEXT_FAILD");
                        mContext.sendBroadcast(new Intent(BLACK_TEXT_FAILD));
                    }
                } else if (item[1] == 0x03) {
                    if (item[2] == 0x00) {
                        //白校准成功
                        Log.i("sendBroadcast","WHITE_TEXT_SUCCESS");
                        mContext.sendBroadcast(new Intent(WHITE_TEXT_SUCCESS));
                    } else if (item[2] == 0x01) {
                        //白校准失败
                        Log.i("sendBroadcast","WHITE_TEXT_FAILD");
                        mContext.sendBroadcast(new Intent(WHITE_TEXT_FAILD));
                    }
                }
            } else {
                if (item[0] == (byte) 0xbb && item[1] == 0x01 && item[2] == 0x00) {
                    index = 0;
                    result = new byte[327];
                }
                System.arraycopy(item, 0, result, index, item.length);
                index += item.length;
//            if(item[item.length-4]==0x00&&item[item.length-3]==0x00&&item[item.length-2]==0xff){//                Log.i("result",clsPublic.bytesToHexString(mStringBuffer.toString().getBytes())) ;
//            }
                if (item.length == 6) {
                    testResultData = new TestResultData(result);
                    Log.i("testResultData", testResultData.toString());
//                Log.i("result", clsPublic.bytesToHexString(result));
                    mContext.sendBroadcast(new Intent(RECEIVE_TEST_DATA));
                }
//            if (characteristic.equals(mBTValueCharacteristic)) {
////                getAndDisplayHrValue();
//            }
            }
        }

        //读取到的数据存在characteristic当中，可以通过characteristic.getValue();函数取出。然后再进行解析操作。
        //int charaProp = characteristic.getProperties();if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0)表示可发出通知。  判断该Characteristic属性
        @Override//当读取设备时会回调该函数
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            Log.i("onCharacteristicRead", clsPublic.bytesToHexString(characteristic.getValue()));
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.i("onCharacteristicWrite", clsPublic.bytesToHexString(characteristic.getValue()));
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        }
    };

    private void getHrCharacteristic() {
        mBTValueCharacteristic = mBTService.getCharacteristic(unknowncharacteristic);
        if (mBTValueCharacteristic == null) {
            Log.i("getHrCharacteristic", "Could not find Heart Rate Measurement Characteristic");
        } else {
            Log.i("getHrCharacteristic", "Heart Rate Measurement characteristic retrieved properly");
            enableNotificationForHr();
        }
    }

    private void enableNotificationForHr() {
        Log.i("enableNotificationForHr", "Enabling notification for Heart Rate");
        boolean success = mBluetoothGatt.setCharacteristicNotification(mBTValueCharacteristic, true);
        if (!success) {
            Log.i("enableNotificationForHr", "Enabling notification failed!");
            return;
        }

//        BluetoothGattDescriptor descriptor = mBTValueCharacteristic.getDescriptor(BleDefinedUUIDs.Descriptor.CHAR_CLIENT_CONFIG);
//        if (descriptor != null) {
//            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//            mBluetoothGatt.writeDescriptor(descriptor);
//            Log.i("enableNotificationForHr", "Notification enabled");
//        } else {
//            Log.i("enableNotificationForHr", "Could not get descriptor for characteristic! Notification are not enabled.");
//        }
    }

    public boolean writeToCharacteristic(byte[] bytes) {

        final int charaProp = mBTValueCharacteristic.getProperties();
        //如果该char可写
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
            // If there is an active notification on a characteristic, clear
            // it first so it doesn't update the data field on the user interface.
            //读取数据，数据将在回调函数中
            //mBluetoothLeService.readCharacteristic(characteristic);
//                byte[] value = new byte[20];
//                value[0] = (byte) 0x00;
//                if (edittext_input_value.getText().toString().equals("")) {
//                    Toast.makeText(getApplicationContext(), "请输入！", Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                mBTValueCharacteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
            mBTValueCharacteristic.setValue(bytes);
            if (mBluetoothGatt.writeCharacteristic(mBTValueCharacteristic)) {
                Log.i("writeCharacteristic", "写入成功");
                return true;
            }
//                    Toast.makeText(getApplicationContext(), "写入成功！", Toast.LENGTH_SHORT).show();
//                }
        }
//            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
//                mNotifyCharacteristic = characteristic;
//                mBluetoothLeService.setCharacteristicNotification(characteristic, true);
//            }
//            edittext_input_value.setText("");

        return false;
    }

    public void disconect() {
//        mBluetoothAdapter = null;
//        mBluetoothDevices = null;
//        mSelectedDevices = null;
//        mBluetoothGatt = null;
//        mSelectBLEDriveDialog = null;
//        mHandler = null;
//        mServiceConnection = null;
//        mBluetoothLeService = null;
    }

//    public boolean wirteCharacteristic(BluetoothGattCharacteristic characteristic) {
//
//        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
//            Log.w(TAG, "BluetoothAdapter not initialized");
//        } else {
//            return;
//        }
//        return false;
//    }
}
