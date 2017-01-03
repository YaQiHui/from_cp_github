package com.color.measurement.from_cp.UI.Activitys.instrument_600.TableC;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.color.measurement.from_cp.Manager.SP.SharePreferenceHelper;
import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.color.measurement.from_cp.Utils.ColorAssist.Contast1;
import com.color.measurement.from_cp.Utils.Math.MathUtil;
import com.color.measurement.from_cp.Utils.color;

/**
 * Created by wpc on 2016/12/19.
 */

public class AdapterForLABResult extends BaseAdapter {

    LayoutInflater mInflater;
    private Activity context;

    ArrayList<String> titles;
    private String[] con_index = new String[]{"by", "sy", "rc", "wc"};

    private HashMap<String, HashMap<String, Double>> table_data;
    HashMap<String, Float> sets;
    List<Double> stand_XYZ, test_XYZ;

    public AdapterForLABResult(Activity context, @Nullable ArrayList<String> titles, @Nullable List<Double> stand, @Nullable List<Double> test) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        if (titles != null) {
            this.titles = titles;
            sets = getSettings();
        }
        initTableData(stand, test);
    }

    private HashMap<String, Float> getSettings() {
        SharedPreferences sp = SharePreferenceHelper.getInstance();
        HashMap<String, Float> sets = new HashMap<>();
        for (int i = 0; i < titles.size(); i++) {
            sets.put(titles.get(i), sp.getFloat(titles.get(i), 2.0f));
        }
        Log.i("getSettings",sets.toString());
        return sets;
    }


    private void initTableData(List<Double> stand, List<Double> test) {

        boolean stand_not_null = stand != null;
        boolean test_not_null = test != null;
        if (stand_not_null)
            stand_XYZ = color.CIELabtoXYZ(stand.get(0), stand.get(1), stand.get(2), Contast1.ILLUMINANT_D65, 0);
        if (test_not_null)
            test_XYZ = color.CIELabtoXYZ(test.get(0), test.get(1), test.get(2), Contast1.ILLUMINANT_D65, 0);

        table_data = new HashMap<>();
        for (int i = 0; i < app.check_state.length; i++) {
            List<Double> stand_ret = null;
            List<Double> test_ret = null;
            if (app.check_state[i]) {
                if (stand_not_null) {
                    stand_ret = getReturnListWithName(stand_XYZ, AddTitleDialog.method_name[i]);
                }
                if (test_not_null) {
                    test_ret = getReturnListWithName(test_XYZ, AddTitleDialog.method_name[i]);
                }
                int id = AddTitleDialog.type_id[i];
                String[] titles = context.getResources().getStringArray(id);

                for (int j = 0; j < titles.length; j++) {
                    HashMap<String, Double> datas = new HashMap<>();
                    datas.put(con_index[0], stand_not_null ? stand_ret.get(j) : null);
                    datas.put(con_index[1], test_not_null ? test_ret.get(j) : null);
                    datas.put(con_index[2], (double) (sets.get(titles[j])));
                    datas.put(con_index[3], stand_not_null && test_not_null ? test_ret.get(j) - stand_ret.get(j) : null);
                    table_data.put(titles[j], datas);
                }
            }
        }

    }

    List<Double> ret;

    List<Double> getReturnListWithName(List<Double> XYZ, String name) {
        try {
            String className = "com.color.measurement.from_cp.Utils.color";
            Class<?> clazz = Class.forName(className);
            // 调用私有方法,必须要用getDeclaredMethod，而不能用getMethod；
            Method mp = clazz.getMethod(name, Double.class, Double.class, Double.class, int.class, int.class);
            mp.setAccessible(true);
            ret = (List<Double>) mp.invoke(List.class, XYZ.get(0), XYZ.get(1), XYZ.get(2), Contast1.ILLUMINANT_D65, 0);
            return ret;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            System.out.println("此处接收被调用方法内部未被捕获的异常");
            e.printStackTrace();
            return ret;
        }
    }

    @Override
    public int getCount() {
        return table_data.size();
    }

    @Override
    public Object getItem(int position) {
        return table_data.get(titles.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lv_instrument, parent, false);
            vh = new ViewHolder();
            vh.title = (TextView) convertView.findViewById(R.id.title_item_lv_instrument);
            vh.by = (TextView) convertView.findViewById(R.id.by_item_lv_instrument);
            vh.sy = (TextView) convertView.findViewById(R.id.sy_item_lv_instrument);
            vh.rc = (TextView) convertView.findViewById(R.id.s_d_item_lv_instrument);
            vh.wc = (TextView) convertView.findViewById(R.id.d_item_lv_instrument);
            vh.result = (TextView) convertView.findViewById(R.id.tv_item_result_result);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.title.setText(titles.get(position));
        HashMap<String, Double> itemData = table_data.get(titles.get(position));
        Log.i("data_size", table_data.size() + "");
        if (itemData.size() != 0) {
//            Log.i("getView",itemData.get(con_index[0]));
            vh.by.setText(itemData.get(con_index[0]) != null ? MathUtil.saveShortDouble(itemData.get(con_index[0])) : "--");
            vh.sy.setText(itemData.get(con_index[1]) != null ? MathUtil.saveShortDouble(itemData.get(con_index[1])) : "--");
            vh.rc.setText(itemData.get(con_index[2])!=null? MathUtil.saveShortDouble(itemData.get(con_index[2])) +"" : "--");
            if (itemData.get(con_index[0]) != null && itemData.get(con_index[1]) != null) {
                double wc = itemData.get(con_index[1]) - itemData.get(con_index[0]);
                vh.wc.setText(MathUtil.saveShortDouble(wc));
                if (sets != null) {
                    if (Math.abs(wc) - sets.get(titles.get(position)) > 0) {
                        vh.result.setTextColor(Color.RED);
                        vh.result.setText("不合格");
                    } else {
                        vh.result.setTextColor(Color.GREEN);
                        vh.result.setText("合格");
                    }
                }
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView title, by, sy, rc, wc, result;
    }

    public HashMap<String, Float> getSets() {
        return sets;
    }

    public void setSets(HashMap<String, Float> sets) {
        this.sets = sets;
    }

    public HashMap<String, HashMap<String, Double>> getTable_data() {
        return table_data;
    }

    public void setTable_data(HashMap<String, HashMap<String, Double>> table_data) {
        this.table_data = table_data;
    }
}
