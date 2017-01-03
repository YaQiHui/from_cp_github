package com.color.measurement.from_cp.UI.Activitys.instrument_600.instrument;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.color.measurement.from_cp.Manager.BLE_4.BLE_Order;
import com.color.measurement.from_cp.Manager.BLE_4.BlueToothManagerForBLE;
import com.color.measurement.from_cp.Manager.BLE_4.TestResultData;
import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.TabSwitcher;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.TableB.Lab;
import com.color.measurement.from_cp.UI.app;
import com.color.measurement.from_cp.Utils.ColorAssist.Contast1;
import com.color.measurement.from_cp.Utils.T;
import com.color.measurement.from_cp.Utils.color;
import com.color.measurement.from_cp.db.DBContanst;
import com.color.measurement.from_cp.db.MyDBHelper;
import com.color.measurement.from_cp.db.MyDBHelper2;
import com.color.measurement.from_cp.others.interfaace.DismissCallback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wpc on 2016/11/8.
 */

public class Instrument extends Fragment implements View.OnClickListener {

    int timeout = 10000;
    private Handler mHandler = new Handler();
    View view;
    Activity mContext;

    TabSwitcher mTabSwitcher;
    boolean standard = false;
    ExpandableListView mExpandableListView;
    ArrayList<GroupData> exp_data; //expandlist  数据
    BlueToothManagerForBLE mBlueToothManagerForBLE;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("onReceive", intent.getAction().toString());
            switch (intent.getAction()) {
                case BlueToothManagerForBLE.INIT_SUCCESS:
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    break;
                case BlueToothManagerForBLE.RECEIVE_TEST_DATA:
                    TestResultData result = mBlueToothManagerForBLE.getTestResultData();
                    float[] SCI = result.getSCI();
                    List XYZ = color.comuteXYZ(SCI, Contast1.ILLUMINANT_D65, 0);
                    List mLab = color.XYZtoHunterLab((double) XYZ.get(0), (double) XYZ.get(1), (double) XYZ.get(2), Contast1.ILLUMINANT_D65, 0);

                    //expandlistview 添加数据
                    if (standard) {
                        SimpleData sta = new SimpleData(true, app.table_name, SCI, null);
                        exp_data.add(new GroupData(sta, new ArrayList<SimpleData>()));
                        selected_group_index = exp_data.size() - 1;
                        refeshExpandLV();
                    } else {
                        if (exp_data != null && exp_data.size() != 0) {
                            List stand = exp_data.get(exp_data.size() - 1).getStandard().getLab();
                            boolean b = comple(stand, mLab, null);
                            SimpleData test = new SimpleData(false, "simple", SCI, b);
                            if (selected_group_index != null) {
                                exp_data.get(selected_group_index).getTest_data().add(test);
                            } else {
                                exp_data.get(exp_data.size() - 1).getTest_data().add(test);
                            }
                            refeshExpandLV();
                        } else {
                            T.show(mContext, "请先录入标样信息");
                        }
                    }
                    mExpandableListView.expandGroup(selected_group_index);
                    //刷新expandlistview

                    Lab l = new Lab((double) mLab.get(0), (double) mLab.get(1), (double) mLab.get(2));
                    mTabSwitcher.refeshChart(SCI, standard);//刷新反射率表
                    mTabSwitcher.setLab(l, standard);//刷新色差图
                    //刷新详情表
                    if (standard) {
                        mTabSwitcher.refeshTable(mLab, null);
                    } else {
//                        if(exp_data.size()>0){
//                            mTabSwitcher.refeshTable(exp_data.get(exp_data.size()-1).getStandard().getLab(),mLab);
//                        }else {
                        mTabSwitcher.refeshTable(null, mLab);
//                        }
                    }
                    break;
                case BlueToothManagerForBLE.BLACK_TEXT_FAILD:
                    T.show(mContext, "黑校准失败");
                    break;
                case BlueToothManagerForBLE.BLACK_TEXT_SUCCESS:
                    T.show(mContext, "黑校准成功");
                    break;
                case BlueToothManagerForBLE.WHITE_TEXT_FAILD:
                    T.show(mContext, "白校准失败");
                    break;
                case BlueToothManagerForBLE.WHITE_TEXT_SUCCESS:
                    T.show(mContext, "白校准成功");
                    break;
                case BlueToothManagerForBLE.RECEIVE_WRONG_DATA:
                    T.show(mContext, "返回数据格式不正确");
                    break;
            }
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }

        }
    };

    private boolean comple(List stand, List mLab, @Nullable List<Double> rc) {
        if (rc != null) {
            for (int i = 0; i < stand.size(); i++) {
                if (Math.abs((double) stand.get(i) - (double) mLab.get(i)) > rc.get(i)) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < stand.size(); i++) {
                if (Math.abs((double) stand.get(i) - (double) mLab.get(i)) > 10) {
                    return false;
                }
            }
        }
        return true;
    }

    Integer groupIndex = null, itemPosition = null;
    private ExpandAdapter mExpandAdapter;
    Set<Integer> expandGroupIndex;
    private ModificateDialog modificate;

    private void refeshExpandLV() {
        mExpandAdapter = new ExpandAdapter(mContext, exp_data,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int groupIndex = (int) v.getTag();
                        final SimpleData stand = exp_data.get(groupIndex).getStandard();
                        modificate = new ModificateDialog(mContext,
                                stand.getName(), stand.getTips(),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (app.changed_name != null)
                                            exp_data.get(groupIndex).getStandard().setName(app.changed_name);
                                        if (app.changed_tips != null)
                                            exp_data.get(groupIndex).getStandard().setTips(app.changed_tips);
                                        refeshExpandLV();
                                    }
                                },
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        exp_data.remove(groupIndex);
                                        refeshExpandLV();
                                    }
                                },
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        exp_data.add(new GroupData(stand, new ArrayList<SimpleData>()));
                                        refeshExpandLV();
                                    }
                                }
                        );
                        modificate.show(mContext.getFragmentManager(), "modificate");
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = (String) v.getTag();
                        String[] chrs = str.split("_");
                        final int groupIndex = Integer.valueOf(chrs[0]);
                        final int itemIndex = Integer.valueOf(chrs[1]);
                        final SimpleData test = exp_data.get(groupIndex).getTest_data().get(itemIndex);
                        modificate = new ModificateDialog(mContext,
                                test.getName(), test.getTips(),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (app.changed_name != null)
                                            exp_data.get(groupIndex).getTest_data().get(itemIndex).setName(app.changed_name);
                                        if (app.changed_tips != null)
                                            exp_data.get(groupIndex).getTest_data().get(itemIndex).setTips(app.changed_tips);
                                        refeshExpandLV();
                                    }
                                },
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        exp_data.get(groupIndex).getTest_data().remove(itemIndex);
                                        refeshExpandLV();
                                    }
                                },
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        exp_data.add(new GroupData(test, new ArrayList<SimpleData>()));
                                        refeshExpandLV();
                                    }
                                }
                        );
                        modificate.show(mContext.getFragmentManager(), "modificate");
                    }
                }
                , groupIndex, itemPosition
        );
        Log.i("GroupIndex+ItemPosition", groupIndex + "_" + itemPosition);
        mExpandableListView.setAdapter(mExpandAdapter);
        //展开所有group
//        if (expandGroupIndex != null) {
//            for (Integer i : expandGroupIndex) {
//                mExpandableListView.expandGroup(i);
//            }
//        }
    }

    ProgressDialog mProgressDialog;
    MyDBHelper mDBHelper;

    MyDBHelper2 mDBHelper2;
    SQLiteDatabase db, db2;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mDBHelper = new MyDBHelper(mContext, DBContanst.CONFIG_NAME, null, 1);
        mDBHelper2 = new MyDBHelper2(mContext, DBContanst.DATA_NAME, null, 1);
        db = mDBHelper.getWritableDatabase();
        db2 = mDBHelper2.getWritableDatabase();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("載入中");
        mProgressDialog.setMessage("請稍后");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        view = inflater.inflate(R.layout.instrument_600_layout, container, false);
        mBlueToothManagerForBLE = BlueToothManagerForBLE.getInstance(mContext);
        mBlueToothManagerForBLE.connectToDevice();
        mTabSwitcher = new TabSwitcher(mContext);
        initData();
        getFragmentManager().beginTransaction().replace(R.id.fl_instrument, mTabSwitcher).commit();
        findView();
        setListener();
        return view;
    }

    private void initData() {
        exp_data = new ArrayList<>();
        expandGroupIndex = new HashSet<>();
    }

    IntentFilter getFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BlueToothManagerForBLE.INIT_SUCCESS);
        intentFilter.addAction(BlueToothManagerForBLE.RECEIVE_WRONG_DATA);
        intentFilter.addAction(BlueToothManagerForBLE.RECEIVE_TEST_DATA);
        intentFilter.addAction(BlueToothManagerForBLE.BLACK_TEXT_SUCCESS);
        intentFilter.addAction(BlueToothManagerForBLE.BLACK_TEXT_FAILD);
        intentFilter.addAction(BlueToothManagerForBLE.WHITE_TEXT_SUCCESS);
        intentFilter.addAction(BlueToothManagerForBLE.WHITE_TEXT_FAILD);
        return intentFilter;
    }

    public void findView() {
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.epl_instrument);
    }

    Integer selected_group_index = 0;

    private void setListener() {
        view.findViewById(R.id.bt_test_instrument).setOnClickListener(this);
        view.findViewById(R.id.bt_test_standard).setOnClickListener(this);
        view.findViewById(R.id.bt_test_black).setOnClickListener(this);
        view.findViewById(R.id.bt_test_white).setOnClickListener(this);
        view.findViewById(R.id.bt_save_all).setOnClickListener(this);

        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                selected_group_index = groupPosition;
                if (exp_data.size() == 0) return;
                expandGroupIndex.add(groupPosition);
                SimpleData stand = exp_data.get(groupPosition).getStandard();
                ArrayList<SimpleData> tests = exp_data.get(groupPosition).getTest_data();
                mTabSwitcher.showGroupData(stand.getLab(), tests);
                for (int i = 0, count = mExpandAdapter.getGroupCount(); i < count; i++) {
                    if (groupPosition != i) {// 关闭其他分组
                        mExpandableListView.collapseGroup(i);
                    }
                }
                Log.i("OnGroupExpandListener", "onGroupExpand");
            }
        });
        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                expandGroupIndex.remove(groupPosition);
            }
        });
        mExpandableListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemPosition = position;
                view.setBackgroundColor(Color.GRAY);
                Log.i("ItemSelectedListener", "onItemSelected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("ItemSelectedListener", "onNothingSelected");
            }
        });
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                SimpleData stand = exp_data.get(groupPosition).getStandard(),
                        test = exp_data.get(groupPosition).getTest_data().get(childPosition);
                mTabSwitcher.refeshTable(stand.getLab(), test.getLab());
                mTabSwitcher.setStandAndTest(stand.getLab(), test.getLab());
                mTabSwitcher.setStandAndTest(stand.getSCI(), test.getSCI());
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mBlueToothManagerForBLE.bindService(mContext);
        mBlueToothManagerForBLE.regeisterReceiver(mContext);
        mContext.registerReceiver(mBroadcastReceiver, getFilter());
    }

    @Override
    public void onPause() {
        super.onPause();
        mBlueToothManagerForBLE.unRegeisterReceiver(mContext);
        mContext.unregisterReceiver(mBroadcastReceiver);
        mBlueToothManagerForBLE.unBindService(mContext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save_all:
                new SaveAsy().execute();
                break;
            case R.id.bt_test_instrument:
                if (exp_data == null | exp_data.size() == 0) {
                    T.show(mContext, "请先录入标样");
                } else {
                    standard = false;
                    mBlueToothManagerForBLE.writeToCharacteristic(BLE_Order.getTestData());
                    mProgressDialog.show();
                }
                postTimeOutReturn();
                break;
            case R.id.bt_test_standard:

                final ArrayList<String> names = getShowData();
                new SelectDBdialog(mContext, names, new DismissCallback() {
                    @Override
                    public void dismiss() {
                        SimpleData stand = hasSandDataInTable(getMappedName(app.table_name));
                        if (stand == null) {
                            standard = true;
                            mBlueToothManagerForBLE.writeToCharacteristic(BLE_Order.getTestData());
                            mProgressDialog.show();
                        } else {
                            exp_data.add(new GroupData(stand, new ArrayList<SimpleData>()));
                            refeshExpandLV();
                        }
                        postTimeOutReturn();
                    }
                }).show(mContext.getFragmentManager(), "select_table");
                break;
            case R.id.bt_test_white:
                mBlueToothManagerForBLE.writeToCharacteristic(BLE_Order.getWriteData());
                mProgressDialog.show();
                postTimeOutReturn();
                break;
            case R.id.bt_test_black:
                mBlueToothManagerForBLE.writeToCharacteristic(BLE_Order.getBlackData());
                mProgressDialog.show();
                postTimeOutReturn();
                break;
        }
    }

    private void postTimeOutReturn() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    T.show(mContext, "传输超时");
                }
            }
        }, timeout);
    }

    private void createTableIfNotExit(ArrayList<GroupData> exp_data) {
        for (GroupData gd : exp_data) {
            insertConfigAndCreateTab(gd.getStandard().getName());
        }
    }

    private void insertConfigAndCreateTab(String str) {

        if (!configContains(str)) {
            MyDBHelper mMyDBHelper = new MyDBHelper(mContext, DBContanst.CONFIG_NAME, null, 1);
            SQLiteDatabase db = mMyDBHelper.getWritableDatabase();
            String mapped = null;
            ContentValues values = new ContentValues();
            Integer id = DBContanst.getMaxId(mContext);
            if (id == null) {
                id = 0;
            }
            mapped = DBContanst.getMappedName(id);
            if (mapped != null) {
                values.put("mapped", mapped);
            }
            values.put("name", str);
            db.insert(DBContanst.CONFIG_TAB_NAME, null, values);
            Log.i("create_table", mapped + "_" + id);
            MyDBHelper2 dbHelper2 = new MyDBHelper2(getActivity(), DBContanst.DATA_NAME, null, 1);
            SQLiteDatabase db2 = dbHelper2.getWritableDatabase();
            db2.execSQL(DBContanst.createTableIfNotExist(mapped));
        }


    }

    private String getMappedName(String table_name) {
        Cursor c = db.query(DBContanst.CONFIG_TAB_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                String name = c.getString(c.getColumnIndex("name"));
                if (name.equals(table_name)) {
                    String map = c.getString(c.getColumnIndex("mapped"));
                    return map;
                }
            } while (c.moveToNext());
        }
        c.close();
        return null;
    }

    private boolean configContains(String table_name) {
        if (db != null) {
            Cursor c = db.query(DBContanst.CONFIG_TAB_NAME, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    String name = c.getString(c.getColumnIndex("name"));
                    if (name.equals(table_name)) {
                        return true;
                    }
                } while (c.moveToNext());
            }
            c.close();
        }
        return false;
    }

    private SimpleData hasSandDataInTable(@Nullable String table_name) {
        if (table_name == null) {
            return null;
        }
        Cursor c = db2.query(table_name, null, null, null, null, null, null);
        if (c.getCount() == 0) {
            return null;
        } else {
            c.moveToFirst();
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            float[] SCI = new float[36];
            if (id == 1) {
                for (int i = 0; i < 36; i++) {
                    SCI[i] = (float) c.getDouble(c.getColumnIndex("SCI" + i));
                }
                return new SimpleData(true, name, SCI, null);

            } else {
                return null;
            }
        }
    }


    private void insertAll(ArrayList<GroupData> exp_data) {
        for (int i = 0; i < exp_data.size(); i++) {
            GroupData gd = exp_data.get(i);
            SimpleData stand = gd.getStandard();
            String tab_name = stand.getName();
            if (hasSandDataInTable(getMappedName(tab_name)) == null) {
                InsertSimpleData(db2, getMappedName(tab_name), stand);
            }
            ArrayList<SimpleData> list = gd.getTest_data();
            for (int j = 0; j < list.size(); j++) {
                InsertSimpleData(db2, getMappedName(tab_name), list.get(j));
            }
        }
    }

    //写入样本
    public void InsertSimpleData(SQLiteDatabase db, String name, SimpleData data) {
        ContentValues v = new ContentValues();
        v.put("name", data.getName());
        v.put("time", data.getTime());
        float[] sci = data.getSCI();
        for (int i = 0; i < 36; i++) {
            v.put("SCI" + i, sci[i]);
        }
        List xyz_ = data.getXYZ();
        v.put("x", (Double) xyz_.get(0));
        v.put("y", (Double) xyz_.get(1));
        v.put("z", (Double) xyz_.get(2));
        db.insert(name, null, v);
    }

    MyDBHelper mMyDBHelper;

    //拿到表名集合
    private ArrayList<String> getShowData() {
        ArrayList<String> strs = new ArrayList<>();

        mMyDBHelper = new MyDBHelper(mContext, DBContanst.CONFIG_NAME, null, 1);
        SQLiteDatabase db = mMyDBHelper.getWritableDatabase();

        Cursor cursor0 = db.query(DBContanst.CONFIG_TAB_NAME, null, null, null, null, null, null);
        if (cursor0.moveToFirst()) {
            do {
                String name = cursor0.getString(cursor0.getColumnIndex("name"));
//                String data = cursor0.getString(cursor0.getColumnIndex("data"));
//                int which = cursor0.getInt(cursor0.getColumnIndex("which"));
               /* Log.e("main","book2 name is ==="+ name);
                Log.e("main","book2 which is ==="+ which);
                Log.e("main","book2 data is ==="+ data);*/
                strs.add(name);
            } while (cursor0.moveToNext());
        }
        cursor0.close();
        return strs;
    }

    class SaveAsy extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            mProgressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            if (exp_data != null && exp_data.size() != 0) {
                createTableIfNotExit(exp_data);
                insertAll(exp_data);
                exp_data = new ArrayList<>();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            mProgressDialog.dismiss();
            refeshExpandLV();
            super.onPostExecute(integer);
        }
    }
}
