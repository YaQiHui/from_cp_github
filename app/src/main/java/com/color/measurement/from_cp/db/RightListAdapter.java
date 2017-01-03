package com.color.measurement.from_cp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.color.measurement.from_cp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.color.measurement.from_cp.Utils.color;

import com.color.measurement.from_cp.Utils.ColorAssist.TurnColor;

/**
 * 基本功能：左侧Adapter
 * 创建时间：16/4/14
 * 邮箱：[email]w489657152@gmail.com[/email]
 */
public class RightListAdapter extends BaseAdapter {
    private Context context;
    private  String name;
    private MyDBHelper2 dbHelper2;
    private ArrayList<TabItemBean> tabItemBeenArray;
    private Map<Integer,Boolean> map = new HashMap<>();
    public void setData(String data){
        this.name = data;
        dbHelper2 = new MyDBHelper2(context,DBContanst.DATA_NAME,null,1);
        SQLiteDatabase db =dbHelper2.getWritableDatabase();
        Cursor cursor1 = db.rawQuery("select name from sqlite_master where type='table' order by name", null);
        while(cursor1.moveToNext()){
            //遍历出表名
            String str = cursor1.getString(0);
            //Log.e("System.out", "str==="+str);
        }
        Log.e("main","yunxing===="+name);
        tabItemBeenArray = new ArrayList<>();
        if(name!=null){
            Cursor cursor = db.query(name,null,null,null,null,null,null);
            if(cursor.moveToFirst()){
                do {
                    String nameP = cursor.getString(cursor.getColumnIndex("name"));
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    double x = cursor.getDouble(cursor.getColumnIndex("x"));
                    double y = cursor.getDouble(cursor.getColumnIndex("y"));
                    double z = cursor.getDouble(cursor.getColumnIndex("z"));
                    String remark = cursor.getString(cursor.getColumnIndex("remark"));
                    tabItemBeenArray.add(new TabItemBean(null,null,null,remark,nameP,id,x,y,z));
                }while (cursor.moveToNext());
            }
        }

        notifyDataSetChanged();
    }

    private void initMap() {
        if(tabItemBeenArray ==null || tabItemBeenArray.size()==0){
            return;
        }
        for (int i =0;i<tabItemBeenArray.size();i++){
            map.put(i,false);
        }
    }

    public RightListAdapter(Context context, String name) {
        Log.e("main","RightListAdapter");
        this.context = context;
        this.name = name;
        initMap();
    }

    @Override
    public int getCount() {
        if(tabItemBeenArray==null){
            return 1;
        }
        return tabItemBeenArray.size();
    }

    @Override
    public Object getItem(int arg0) {
        if(tabItemBeenArray==null){
            return 1;
        }
        return tabItemBeenArray.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int arg0, View arg1, ViewGroup arg2) {
        setData(name);
        Holder holder = null;
        if (arg1 == null) {
            holder = new Holder();
            arg1 = LayoutInflater.from(context).inflate(R.layout.right_item, null);
            holder.left_list_item = (TextView) arg1.findViewById(R.id.right_text);
            holder.checkBox = (CheckBox) arg1.findViewById(R.id.ck_ch_top);
            holder.text4 = (TextView) arg1.findViewById(R.id.remark_text);
            holder.text5 = (TextView) arg1.findViewById(R.id.color_left);
            arg1.setTag(holder);
        } else {
            holder = (Holder) arg1.getTag();
        }
        if(ListConstast.IS_SHOW){
            holder.checkBox.setVisibility(View.VISIBLE);
        }else {
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        // 设置CheckBox的状态
        if (map.get(arg0) == null) {
            map.put(arg0, false);
        }
        Log.e("main","map.get(arg0)==="+map.get(arg0));
        holder.checkBox.setChecked(map.get(arg0));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("main","isChecked=="+isChecked);
                Log.e("main","map.get(arg0)=="+map.get(arg0));
                map.put(arg0,isChecked);
            }
        });
        Log.e("main","tabItemBeenArray.size==="+tabItemBeenArray.size());
        if(tabItemBeenArray==null||tabItemBeenArray.size()==0){
            holder.left_list_item.setText("");
        }else {
            double x = tabItemBeenArray.get(arg0).getX();
            double y = tabItemBeenArray.get(arg0).getY();
            double z = tabItemBeenArray.get(arg0).getZ();
            List list = color.XYZtoCIE_LabCH(x,y,z,0x04,0);
            double L = (double) list.get(0);
            double a = (double) list.get(1);
            double b = (double) list.get(2);
            List list2 = color.LabtoRGB(L,a,b);
            double r = (double) list2.get(0);
            double g = (double) list2.get(1);
            double B = (double) list2.get(2);
            holder.left_list_item.setText(tabItemBeenArray.get(arg0).getName());
            holder.text4.setText(tabItemBeenArray.get(arg0).getRemork()+"");
            String str = TurnColor.toHex((int)r,(int)g,(int)B);
            holder.text5.setText(str);
            holder.text5.setBackgroundColor(android.graphics.Color.parseColor(str));

        }
        return arg1;
    }

    //点击item选中CheckBox
    public void setSelectItem(int position) {
        Log.e("main","position==="+position);
        Log.e("main","(map.get(position)==="+(map.get(position)));
        //对当前状态取反
        if (map.get(position)) {
            map.put(position, false);
        } else {
            map.put(position, true);
        }
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getDeletId(){
        setData(name);
        ArrayList<Integer> intList = new ArrayList<>();
        Log.e("main","map.size==="+map.size());
        for (int i= 0; i<map.size();i++){
            if(map.get(i)){
                Log.e("main","i===="+i);
                Log.e("main","tabItemBeenArray===="+tabItemBeenArray.size());
                int id =tabItemBeenArray.get(i).getId();
                intList.add(id);
            }
        }
        return intList;
    }

    //全选
    public void setSelectTrue(){
        for (int i = 0;i<map.size();i++){
            map.put(i,true);
        }
        notifyDataSetChanged();
    }

    //全不选
    public void setSelectFalse(){
        for (int i = 0;i<map.size();i++){
            map.put(i,false);
        }
        notifyDataSetChanged();
    }

    private class Holder {
        private TextView left_list_item;
        private TextView text2;
        private TextView text3;
        private TextView text4;
        private TextView text5;
        private CheckBox checkBox;
    }
}