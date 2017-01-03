package com.color.measurement.from_cp.UI.Activitys.instrument_600;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;

import com.color.measurement.from_cp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.color.measurement.from_cp.Utils.ColorAssist.DialogUtils;
import com.color.measurement.from_cp.Utils.ColorAssist.T;
import com.color.measurement.from_cp.Utils.color;
import com.color.measurement.from_cp.db.ChoseFileDialog;
import com.color.measurement.from_cp.db.DBContanst;
import com.color.measurement.from_cp.db.ExcelHelper;
import com.color.measurement.from_cp.db.FileAndPath;
import com.color.measurement.from_cp.db.FileInfo;
import com.color.measurement.from_cp.db.FileUtils;
import com.color.measurement.from_cp.db.LeftListAdapter;
import com.color.measurement.from_cp.db.ListConstast;
import com.color.measurement.from_cp.db.LoginDialogFragment;
import com.color.measurement.from_cp.db.MyDBHelper;
import com.color.measurement.from_cp.db.MyDBHelper2;
import com.color.measurement.from_cp.db.NameBean;
import com.color.measurement.from_cp.db.NameBeanSelect;
import com.color.measurement.from_cp.db.RightListAdapter;
import com.color.measurement.from_cp.db.SPUtils;
import com.color.measurement.from_cp.db.SimpleData;

/**
 * Created by wpc on 2016/11/8.
 */

public class DB extends Fragment implements View.OnClickListener {

    private View view;
    private SwipeRefreshLayout swip;
    private boolean IS_CHECK = false;
    private CheckBox topCk;
    private Button addBt, tianBt, chaBt, reBt;
    private Map<String, List<String>> dataset = new HashMap<>();
    private String[] parentList = new String[]{"first", "second", "third"};
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> map = new HashMap<>();
    private MyDBHelper dbHelper;
    private ListView leftList;
    private ListView rightList;
    private ArrayList<NameBean> nameBeen;
    private List<String> nameOrig = new ArrayList<>();
    private List<String> nameSecond = new ArrayList<>();
    private String tabName;
    private MyDBHelper2 dbHelper2;
    private boolean[] flagArray = new boolean[100];
    private CheckBox checkBox;
    private LeftListAdapter adapterLeft;
    private RightListAdapter adapterRight;
    private ArrayList<Integer> idArray;
    private LocalBroadcastManager mManager;
    private MyBroadCastReceiver myBroadCastReceiver;
    private double x;
    private double y;
    private double z;
    private AlertDialog.Builder builder;
    private EditText editText;
    private ArrayList<NameBean> nameFind;
    private ArrayList<NameBeanSelect> beanSelects;
    private List<String> stringList;
    private ChoseFileDialog choseFileDialog;
    private ArrayList<FileInfo> files;
    private ArrayList<SimpleData> mData;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.db_600_layout, container, false);
        dbHelper = new MyDBHelper(getActivity(), DBContanst.CONFIG_NAME, null, 1);
        initData();
        return view;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
            }
        }
    };

    private void initData() {
        builder = new AlertDialog.Builder(getActivity());
        //createDB();
        registerMyReceiver();//注册广播
        findAndSet();
        checkBox = (CheckBox) view.findViewById(R.id.ck_all_top);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    adapterRight.setSelectTrue();
                } else {
                    adapterRight.setSelectFalse();
                }
            }
        });
        stringList = getNameOrig();
        adapterLeft = new LeftListAdapter(getActivity(), flagArray, stringList);
        leftList.setAdapter(adapterLeft);
        adapterRight = new RightListAdapter(getActivity(), tabName);
        rightList.setAdapter(adapterRight);
        leftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SPUtils.put(getActivity(), "leftid", position);
                nameBeen = getNameBeen();
                for (int i = 0; i < nameBeen.size(); i++) {
                    if (i == position) {
                        flagArray[i] = true;
                    } else {
                        flagArray[i] = false;
                    }
                }
                rightList.setVisibility(View.VISIBLE);
                if (nameSecond.size() == 0 ) {
                    adapterRight.setData(nameBeen.get(position).getName());
                    SPUtils.put(getActivity(), "name", nameBeen.get(position).getName());
                } else {
                    adapterRight.setData(nameSecond.get(position));
                    SPUtils.put(getActivity(), "name", nameSecond.get(position));
                    Log.e("mian", "nameSecond..position ==" + position);
                    Log.e("main", "nameSecond.get(position)==" + nameSecond.get(position));
                }
                adapterLeft.notifyDataSetChanged();
                adapterRight.notifyDataSetChanged();
            }
        });

        leftList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                DialogUtils.showDialog(getActivity(), "确定删除此条目？", "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tabName = nameBeen.get(position).getName();
                        Log.e("main", "tabanme=======" + tabName);
                        dbHelper = new MyDBHelper(getActivity(), DBContanst.CONFIG_NAME, null, 1);
                        dbHelper.getWritableDatabase();
                        SQLiteDatabase dbDe = dbHelper.getWritableDatabase();
                        dbDe.delete(DBContanst.CONFIG_TAB_NAME, "name = ?", new String[]{tabName + ""});
                        adapterLeft = new LeftListAdapter(getActivity(), flagArray, getNameOrig());
                        leftList.setAdapter(adapterLeft);
                        rightList.setVisibility(View.INVISIBLE);
                        // nameBeen = getNameBeen();
                        //adapterRight.setData(nameBeen.get(position).getName());
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }, false);
                return false;
            }
        });

        rightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tabName = (String) SPUtils.get(getActivity(), "name", "yangben");
                idArray = new ArrayList<Integer>();
                dbHelper2 = new MyDBHelper2(getActivity(), DBContanst.DATA_NAME, null, 1);
                SQLiteDatabase db = dbHelper2.getWritableDatabase();
                Cursor cursor = db.query(tabName, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String nameP = cursor.getString(cursor.getColumnIndex("name"));
                        x = cursor.getDouble(cursor.getColumnIndex("x"));
                        y = cursor.getDouble(cursor.getColumnIndex("y"));
                        z = cursor.getDouble(cursor.getColumnIndex("z"));
                        int idB = cursor.getInt(cursor.getColumnIndex("id"));
                        Log.e("main", "nameXXX====" + idB);
                        idArray.add((int) id);
                    } while (cursor.moveToNext());
                    cursor.moveToLast();
                }

                Log.e("main", "ListConstast.IS_SHOW===" + ListConstast.IS_SHOW);
                if (ListConstast.IS_SHOW) {
                    Log.e("main", "运行了不显示");
                    adapterRight.setSelectItem(position);
                } else {
                    List list = color.XYZtoCIE_LabCH(x, y, z, 0x04, 0);
                    Log.e("main", "idArray.get(position)===" + idArray.get(position));
                    LoginDialogFragment editNameDialogFragment = new LoginDialogFragment(getActivity(), idArray.get(position), tabName);
                    editNameDialogFragment.show(getActivity().getFragmentManager(), "EditNameDialog");
                    //T.showShort(getActivity(),list.get(0)+"G==="+list.get(1)+"B===="+list.get(2));
                }
            }
        });
    }

    private void createDB() {
        dbHelper = new MyDBHelper(getActivity(), DBContanst.CONFIG_NAME, null, 1);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valueF = new ContentValues();
        valueF.put("name", "yangben");
        db.insert(DBContanst.CONFIG_TAB_NAME, null, valueF);
        valueF.clear();

        valueF.put("name", "two");
        db.insert(DBContanst.CONFIG_TAB_NAME, null, valueF);
        valueF.clear();

        valueF.put("name", "three");
        db.insert(DBContanst.CONFIG_TAB_NAME, null, valueF);
        valueF.clear();

        valueF.put("name", "four");
        db.insert(DBContanst.CONFIG_TAB_NAME, null, valueF);


        nameBeen = getNameBeen();

        dbHelper2 = new MyDBHelper2(getActivity(), DBContanst.DATA_NAME, null, 1);
        SQLiteDatabase db3 = dbHelper2.getWritableDatabase();
        for (int i = 0; i < nameBeen.size(); i++) {
            //db3.execSQL(DBContanst.getTabName(DBContanst.getMappedName(DBContanst.getMaxId(getActivity()))));
            db3.execSQL(DBContanst.createTableIfNotExist(nameBeen.get(i).getName()));
        }

        dbHelper2 = new MyDBHelper2(getActivity(), DBContanst.DATA_NAME, null, 1);
        dbHelper2.getWritableDatabase();
        SQLiteDatabase dbT = dbHelper2.getWritableDatabase();
        Cursor cursor1 = dbT.rawQuery("select name from sqlite_master where type='table' order by name", null);
        while (cursor1.moveToNext()) {
            //遍历出表名
            String str = cursor1.getString(0);
            Log.e("mian", "str=====" + str);
        }
        ContentValues valueFT = new ContentValues();
        Log.e("main", "nameBeen.size()===" + nameBeen.size());
        //for(int i =0;i<nameBeen.size();i++){
        //模拟插入数据
        valueFT.put("name", "标样第一组");
        valueFT.put("time", "10:");
        valueFT.put("id", 0);
        valueFT.put("which", 0);
        valueFT.put("x", 74.75);
        valueFT.put("y", 79.43);
        valueFT.put("z", 81.42);
        valueFT.put("remark", "备注1号");
        dbT.insert("yangben", null, valueFT);
        valueFT.clear();
        valueFT.put("name", "试样第一组1号");
        valueFT.put("time", "11:");
        valueFT.put("id", 1);
        valueFT.put("which", 1);
        valueFT.put("x", 74.75);
        valueFT.put("y", 79.43);
        valueFT.put("z", 81.42);
        valueFT.put("remark", "备注1号");
        dbT.insert("yangben", null, valueFT);
        valueFT.put("name", "标样第二组");
        valueFT.put("time", "10:");
        valueFT.put("id", 0);
        valueFT.put("which", 0);
        valueFT.put("x", 74.75);
        valueFT.put("y", 79.43);
        valueFT.put("z", 81.42);
        valueFT.put("remark", "备注1号");
        dbT.insert("two", null, valueFT);
        valueFT.clear();
        valueFT.put("name", "试样第二组1号");
        valueFT.put("time", "11:");
        valueFT.put("id", 1);
        valueFT.put("which", 1);
        valueFT.put("x", 74.75);
        valueFT.put("y", 79.43);
        valueFT.put("remark", "备注1号");
        valueFT.put("z", 81.42);
        dbT.insert("two", null, valueFT);

        valueFT.put("name", "标样第三组");
        valueFT.put("time", "10:");
        valueFT.put("id", 0);
        valueFT.put("which", 0);
        valueFT.put("x", 74.75);
        valueFT.put("y", 79.43);
        valueFT.put("z", 81.42);
        valueFT.put("remark", "备注1号");
        dbT.insert("three", null, valueFT);
        valueFT.clear();
        valueFT.put("name", "试样第三组1号");
        valueFT.put("time", "11:");
        valueFT.put("id", 1);
        valueFT.put("which", 1);
        valueFT.put("x", 74.75);
        valueFT.put("y", 79.43);
        valueFT.put("z", 81.42);
        valueFT.put("remark", "备注1号");
        dbT.insert("three", null, valueFT);

        valueFT.put("name", "标样第四组");
        valueFT.put("time", "10:");
        valueFT.put("id", 0);
        valueFT.put("which", 0);
        valueFT.put("x", 74.75);
        valueFT.put("y", 79.43);
        valueFT.put("z", 81.42);
        valueFT.put("remark", "备注1号");
        dbT.insert("four", null, valueFT);
        valueFT.clear();
        valueFT.put("name", "试样第四组1号");
        valueFT.put("time", "10:");
        valueFT.put("id", 1);
        valueFT.put("which", 1);
        valueFT.put("x", 74.75);
        valueFT.put("y", 79.43);
        valueFT.put("z", 81.42);
        valueFT.put("remark", "备注1号");
        dbT.insert("four", null, valueFT);
        valueFT.clear();
        valueFT.put("name", "试样第四组2号");
        valueFT.put("time", "11:");
        valueFT.put("id", 2);
        valueFT.put("which", 1);
        valueFT.put("x", 74.75);
        valueFT.put("y", 79.43);
        valueFT.put("z", 81.42);
        valueFT.put("remark", "备注1号");
        dbT.insert("four", null, valueFT);

    }

    private void findAndSet() {
        addBt = (Button) view.findViewById(R.id.first_bt);
        addBt.setOnClickListener(this);
        tianBt = (Button) view.findViewById(R.id.second_bt);
        tianBt.setOnClickListener(this);
        chaBt = (Button) view.findViewById(R.id.third_bt);
        chaBt.setOnClickListener(this);
        reBt = (Button) view.findViewById(R.id.fourth_bt);
        reBt.setOnClickListener(this);
        leftList = (ListView) view.findViewById(R.id.left_listview);
        rightList = (ListView) view.findViewById(R.id.right_listview);
        editText = (EditText) view.findViewById(R.id.textView2);
        editText.addTextChangedListener(new MyTextWatcher());
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                T.showShort(getActivity(),"完成刷新");
                stringList = getNameOrig();
                adapterLeft = new LeftListAdapter(getActivity(), flagArray, stringList);
                leftList.setAdapter(adapterLeft);
                adapterRight = new RightListAdapter(getActivity(), tabName);
                rightList.setAdapter(adapterRight);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 输入框搜索监听
     *
     * @author Administrator
     */
    class MyTextWatcher implements TextWatcher {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            nameSecond.clear();
            if (str.length() == 0) {
                adapterLeft = new LeftListAdapter(getActivity(), flagArray, stringList);
                leftList.setAdapter(adapterLeft);
            } else {
                ListConstast.EDITTEST = true;
                dbHelper = new MyDBHelper(getActivity(), DBContanst.CONFIG_NAME, null, 1);
                dbHelper.getWritableDatabase();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor1 = db.query(DBContanst.CONFIG_TAB_NAME, null, null, null, null, null, null);
                nameFind = new ArrayList<>();
                if (cursor1.moveToFirst()) {
                    do {
                        String nameP = cursor1.getString(cursor1.getColumnIndex("name"));
                        nameFind.add(new NameBean(nameP));
                    } while (cursor1.moveToNext());
                }
                Log.e("main", "是否包含===" + nameFind.get(0).getName().contains(str));
                for (int i = 0; i < nameFind.size(); i++) {
                    if (nameFind.get(i).getName().contains(str) && str.length() != 0) {
                        nameSecond.add(nameFind.get(i).getName());
                    }
                }
                for (int i = 0; i < nameSecond.size(); i++) {
                    Log.e("mian", "nameSecond==" + nameSecond.get(i));
                }
                adapterLeft = new LeftListAdapter(getActivity(), flagArray, nameSecond);
                leftList.setAdapter(adapterLeft);
            }
        }
    }

    //加载数据
    private void loadData() {

    }

    private void showProgress() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_bt:
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("选择操作");
                builder.setItems(new String[]{"导出Excel", "管理Excel文件"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        if (which == 0) {
                            FileAndPath.createOrExistsDir(FileAndPath.Excel);
                            Log.e("main","mData=="+mData.get(0).getId());
                            int size = mData.size();
                            if (size != 0) {
                                String filename = mData.get(0).getId() + "_" + mData.get(size - 1).getId();
                                Log.e("mian","new file=="+ FileAndPath.Excel + "/" + filename + ".xls");
                                if (new File(FileAndPath.Excel, filename + ".xls").exists()) {
                                    T.showShort(getActivity(), "文件已存在请勿重复创建");
                                } else {
                                    Log.e("mian","ExcelHelper=="+ ExcelHelper.writeExcel(mData, ExcelHelper.title, FileAndPath.Excel, filename, ExcelHelper.ExcelOutPutType.poi));
                                    if (ExcelHelper.writeExcel(mData, ExcelHelper.title, FileAndPath.Excel, filename, ExcelHelper.ExcelOutPutType.jxl)) {
                                        T.showShort(getActivity(), "导出成功" + FileAndPath.Excel + "/" + filename + ".xls");
                                    }
                                }

                            } else {
                                T.showShort(getActivity(), "没有数据");
                            }

                        } else {
                            files = FileUtils.getFileInfoListWithDirPathAndEnd(FileAndPath.Excel, ".xls");
                            if (files.size() != 0) {
                                //                                        FileUtils.playFileWithSystemSeveice((Activity) context, abs);
//                                                files = FileUtils.getFileInfoListWithDirPathAndEnd(FileUtils.EXCEL_OUTPUT_PATH, ".xls");
                                choseFileDialog = new ChoseFileDialog(getActivity(), files, new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        FileInfo select = files.get(position);
                                        String abs = select.getDirPath() + "/" + select.getName();
                                        FileUtils.openFile(getActivity(), new File(abs));
//                                        FileUtils.playFileWithSystemSeveice((Activity) context, abs);
                                    }
                                }, new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                                        choseFileDialog.dismiss();
                                        FileInfo select = files.get(position);
                                        final String abs = select.getDirPath() + "/" + select.getName();
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                        builder1.setTitle("删除文件");
                                        builder1.setMessage("是否删除文件" + abs);
                                        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (FileUtils.deleteFile(abs)) {
//                                                files = FileUtils.getFileInfoListWithDirPathAndEnd(FileUtils.EXCEL_OUTPUT_PATH, ".xls");
                                                    T.showShort(getActivity(), "删除成功");
                                                }
                                            }
                                        });
                                        builder1.setNegativeButton("取消", null);
                                        builder1.create().show();
                                        return false;
                                    }
                                });
                                choseFileDialog.show(((Activity) getActivity()).getFragmentManager(), "chosefiledialog");
                            } else {
                                T.showShort(getActivity(), "没有该类型的文件");
                            }

                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;
            case R.id.second_bt:
                mData = new ArrayList<>();
                mData.add(new SimpleData(1,"2","2","2","2","2","2"));
                break;
            case R.id.fourth_bt://删除
                if (checkBox.getVisibility() == View.INVISIBLE) {
                    T.showShort(getActivity(), "请先勾选，再删除");
                    return;
                }
                String tabName = (String) SPUtils.get(getActivity(), "name", "yangben");
                Log.e("main", "tabanme=======" + tabName);
                dbHelper2 = new MyDBHelper2(getActivity(), DBContanst.DATA_NAME, null, 1);
                dbHelper2.getWritableDatabase();
                SQLiteDatabase dbDe = dbHelper2.getWritableDatabase();
                ArrayList<Integer> list = adapterRight.getDeletId();
                Log.e("main", "list.size()===" + list.size());
                for (int i = 0; i < list.size(); i++) {
                    int id = list.get(i);
                    Log.e("main", "id===" + id);
                    dbDe.delete(tabName, "id = ?", new String[]{id + ""});
                    adapterRight.notifyDataSetChanged();
                    rightList.invalidate();
                }
                break;
        }
    }

    private ArrayList<NameBean> getNameBeen() {
        Log.e("main", "getNAmeBean659");
        nameBeen = new ArrayList<>();
        dbHelper = new MyDBHelper(getActivity(), DBContanst.CONFIG_NAME, null, 1);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db0 = dbHelper.getWritableDatabase();
        Cursor cursor0 = db0.query(DBContanst.CONFIG_TAB_NAME, null, null, null, null, null, null);
        if (cursor0.moveToFirst()) {
            do {
                String name = cursor0.getString(cursor0.getColumnIndex("mapped"));
                Log.e("main", "name测试==" + name);
                nameBeen.add(new NameBean(name));
            } while (cursor0.moveToNext());
        }
        cursor0.close();
        return nameBeen;
    }

    private List<String> getNameOrig() {
        nameOrig.clear();
        Log.e("main", "getNAmeBean659");
        dbHelper = new MyDBHelper(getActivity(), DBContanst.CONFIG_NAME, null, 1);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db0 = dbHelper.getWritableDatabase();
        Cursor cursor0 = db0.query(DBContanst.CONFIG_TAB_NAME, null, null, null, null, null, null);
        if (cursor0.moveToFirst()) {
            do {
                String name = cursor0.getString(cursor0.getColumnIndex("name"));
                //Log.e("main","name测试=="+name);
                nameOrig.add(name);
            } while (cursor0.moveToNext());
        }
        cursor0.close();
        Log.e("mian", "nameOrig===" + nameOrig.size());
        return nameOrig;
    }

    //注册广播
    private void registerMyReceiver() {
        mManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter filter = new IntentFilter();
        filter.addAction("ui");
        filter.addAction("ui2");
        filter.addAction("turnre");
        myBroadCastReceiver = new MyBroadCastReceiver();
        mManager.registerReceiver(myBroadCastReceiver, filter);
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "ui"://显示checkbox
                    Log.e("mian", "收到UI广播");
                    ListConstast.IS_SHOW = true;
                    adapterRight.notifyDataSetChanged();
                    checkBox.setVisibility(View.VISIBLE);
                    break;
                case "ui2"://隐藏checkBox
                    Log.e("main", "收到ui2广播");
                    ListConstast.IS_SHOW = false;
                    ListConstast.IS_ALL_CHECKED = false;
                    checkBox.setChecked(false);
                    adapterRight.setSelectFalse();
                    adapterRight.notifyDataSetChanged();
                    checkBox.setVisibility(View.INVISIBLE);
                    break;
                case "turnre":
                    Log.e("main", "收到turn广播");
                    String re = (String) SPUtils.get(getActivity(), "re", "");
                    String id = (String) SPUtils.get(getActivity(), "id", "");
                    String ming = (String) SPUtils.get(getActivity(),"ming","");
                    T.showShort(getActivity(), re);
                    dbHelper2 = new MyDBHelper2(getActivity(), DBContanst.DATA_NAME, null, 1);
                    dbHelper2.getWritableDatabase();
                    SQLiteDatabase dbTu = dbHelper2.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("remark", re);
                    values.put("name",ming);
                    Log.e("mian", "id========" + id);
                    String tabName = (String) SPUtils.get(getActivity(), "name", "yangben");
                    Log.e("main", "tabname===" + tabName);
                    dbTu.update(tabName, values, "id = ?", new String[]{id + ""});
                    Cursor cursor = dbTu.query(tabName, null, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        do {
                            String nameP = cursor.getString(cursor.getColumnIndex("name"));
                            Log.e("mian", "name 改变========" + nameP);
                        } while (cursor.moveToNext());
                    }
                    handler.sendEmptyMessage(0);
                    adapterRight.setData(tabName);
                    adapterRight.notifyDataSetChanged();

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mManager.unregisterReceiver(myBroadCastReceiver);
    }
}
