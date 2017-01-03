package com.color.measurement.from_cp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.color.measurement.from_cp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 基本功能：左侧Adapter
 * 创建时间：16/4/14
 * 邮箱：[email]w489657152@gmail.com[/email]
 */
public class LeftListAdapter extends BaseAdapter {
    boolean[] flagArray;
    private Context context;
    private MyDBHelper dbHelper;
    private List<String> list;


    public LeftListAdapter(Context context, boolean[] flagArray,
                           List<String> list) {
        this.context = context;
        this.flagArray = flagArray;
        this.list = list;
        Log.e("main","LeftListAdapter");
    }

    public List<String> getNameBeen() {
        list = new ArrayList<>();
        dbHelper = new MyDBHelper(context, DBContanst.CONFIG_NAME, null, 1);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db0 = dbHelper.getWritableDatabase();
        Cursor cursor0 = db0.query("Tab1", null, null, null, null, null, null);
        if (cursor0.moveToFirst()) {
            do {
                String name = cursor0.getString(cursor0.getColumnIndex("name"));
                Log.e("main", "book2 name is ===" + name);
                list.add(name);
            } while (cursor0.moveToNext());
        }
        cursor0.close();
        return list;
    }

    @Override
    public int getCount() {
        Log.e("main", "bean.size()==" + list.size());
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        Log.e("main", "bean.size() get==" + list.size());
        Log.e("main","LeftListAdapter.getView");
        //list = getNameBeen();
        Holder holder = null;
        if (arg1 == null) {
            holder = new Holder();
            arg1 = LayoutInflater.from(context).inflate(R.layout.left_list_item, null);
            holder.left_list_item = (TextView) arg1.findViewById(R.id.left_list_item);
            arg1.setTag(holder);
        } else {
            holder = (Holder) arg1.getTag();
        }
        holder.updataView(arg0);
        return arg1;
    }
    private class Holder {
        private TextView left_list_item;
        public void updataView(final int position) {
            if (list.size() == 0) {
                left_list_item.setText("");
            } else {
                left_list_item.setText(list.get(position));
            }
            if (flagArray == null || flagArray.length == 0) {
                return;
            }
            Log.e("main", "flagArray.size===" + flagArray.length);
            if (flagArray[position]) {
                left_list_item.setBackgroundColor(Color.rgb(255, 255, 255));
            } else {
                left_list_item.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }
}