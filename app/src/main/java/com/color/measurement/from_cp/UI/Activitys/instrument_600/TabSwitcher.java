package com.color.measurement.from_cp.UI.Activitys.instrument_600;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.TableA.FSL_table;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.TableA.Line;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.TableA.point;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.TableB.Lab;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.TableB.Light_table;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.TableB.SC_table;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.TableB.onRefeshListener;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.TableC.ResultFragment;
import com.color.measurement.from_cp.UI.Activitys.instrument_600.instrument.SimpleData;
import com.color.measurement.from_cp.UI.view.VerticalTextView;

import java.util.ArrayList;
import java.util.List;

import static com.color.measurement.from_cp.UI.Activitys.instrument_600.Consts.line_colors;
import static com.color.measurement.from_cp.UI.Activitys.instrument_600.Consts.point_colors;

/**
 * Created by wpc on 2016/11/30.
 */

public class TabSwitcher extends Fragment implements onRefeshListener {

    LinearLayout ll;
    VerticalTextView vtv1, vtv2, vtv3;
    int index;
    private Activity mContext;

    //chart 默认值
    int point_r = 5, line_width = 2;

    FSL_table mFSL_table;

    Light_table mLight_table;
    SC_table mSC_table;

    public TabSwitcher(Activity context) {
        mContext = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tables_layout, container, false);
        ll = (LinearLayout) view.findViewById(R.id.fl_tables);
        initFSL();
        initSC();
//        RefeshPoints();
        vtv1 = (VerticalTextView) view.findViewById(R.id.vtv_1);
        vtv2 = (VerticalTextView) view.findViewById(R.id.vtv_2);
        vtv3 = (VerticalTextView) view.findViewById(R.id.vtv_3);
        setListener();

        ll.addView(mFSL_table);
//                getFragmentManager().beginTransaction().replace(R.id.fl_tables, new Table_FSL()).commit();
        vtv1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        vtv2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        vtv3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        index = 1;
//        result = (TextView) view.findViewById(R.id.tv_result_tables);
        return view;
    }

    private void initSC() {
        mLight_table = new Light_table(mContext);
        mLight_table.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2));
        mSC_table = new SC_table(getActivity());
        mSC_table.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
    }

    private void initFSL() {
        mFSL_table = new FSL_table(getActivity());
        mFSL_table.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("FSL", "onLongClick");
                final String[] mods = new String[]{"SCI", "SCE", "SCI+SCE"};
                new AlertDialog.Builder(mContext).setTitle("模式切换").setItems(mods, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFSL_table.setMod(mods[which]);
                    }
                }).setNegativeButton("取消", null).create().show();
                return false;
            }
        });
        mFSL_table.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFSL_table.setUnit("nm");
        mFSL_table.setTipsColors(0xff2E2EF6, 0xfff24e4e, 0xff623062, 0xff32f632);
    }

    void setListener() {
        vtv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index != 1) {
                    ll.removeAllViews();
                    ll.addView(mFSL_table);
//                getFragmentManager().beginTransaction().replace(R.id.fl_tables, new Table_FSL()).commit();
                    vtv1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    vtv2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    vtv3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    index = 1;
                }

            }
        });
        vtv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index != 2) {
                    ll.removeAllViews();
                    ll.addView(mLight_table);
                    ll.addView(mSC_table);
//                getFragmentManager().beginTransaction().replace(R.id.fl_tables, new Table_Lab()).commit();
                    vtv1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    vtv2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    vtv3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    index = 2;
                }

            }
        });
        vtv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index != 3) {
                    ll.removeAllViews();
                    index = 3;
                    refeshTable(null, null);
//                getFragmentManager().beginTransaction().replace(R.id.fl_tables, new Table_FSL()).commit();
                    vtv1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    vtv2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    vtv3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        });
    }


    List<Double> stand_lab;
    List<Double> test_lab;

    public void refeshTable(@Nullable List stand_lab, @Nullable List test_lab) {
        if (stand_lab != null) this.stand_lab = stand_lab;
        if (test_lab != null) this.test_lab = test_lab;
        if (index == 3) {
            mContext.getFragmentManager().beginTransaction().replace(R.id.fl_tables, new ResultFragment(
                    getActivity(),
                    null,
                    this.stand_lab, this.test_lab)).commit();
        }
    }


    ArrayList<point> getPoints(@Nullable float[] datas) {
        ArrayList<point> points = new ArrayList<>();
        if (datas != null) {
            for (int i = 0; i < datas.length; i++) {
                float x = (mFSL_table.getX_end() - mFSL_table.getX_begin()) / (datas.length - 1);
                points.add(new point((mFSL_table.getX_begin() + x * i), datas[i]));
            }
        }

        return points;
    }

    ArrayList<ArrayList<point>> points = new ArrayList<>();

    public void refeshChart(float[] datas, boolean standard) {
        if (standard) {
            if (points.size() != 0) {
                points.remove(0);
            }
            points.add(0, getPoints(datas));
        } else {
            if (points.size() == 0) {
                points.add(getPoints(null));
                points.add(getPoints(datas));
            }
            if (points.size() == 1) {
                points.add(getPoints(datas));
            }
            if (points.size() == 2) {
                points.remove(1);
                points.add(getPoints(datas));
            }

        }
        refeshLines();
    }

    private void refeshLines() {
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            lines.add(new Line(true, point_colors[i], point_r, line_colors[i], line_width, points.get(i)));
        }
        mFSL_table.setLines(lines);
    }

    public void setStandAndTest(float[] stand, float[] test) {
        points.clear();
        points.add(getPoints(stand));
        points.add(getPoints(test));
        refeshLines();
    }


    @Override
    public void setLab(Lab lab, boolean isStand) {
        mLight_table.setLab(lab, isStand);
        mSC_table.setLab(lab, isStand);
    }

    @Override
    public void setStandAndTest(Lab stand, Lab test) {
        mLight_table.setStandAndTest(stand, test);
        mSC_table.setStandAndTest(stand, test);
    }

    public void clearLabs() {
        mLight_table.clearLabs();
        mSC_table.clearLabs();
    }

    public void setStandAndTest(List stand, List test) {
        Lab s = new Lab((double) stand.get(0), (double) stand.get(1), (double) stand.get(2));
        Lab t = new Lab((double) test.get(0), (double) test.get(1), (double) test.get(2));
        setStandAndTest(s, t);
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void showGroupData(List lab, ArrayList<SimpleData> tests) {
        mLight_table.showGroupData(lab, tests);
        mSC_table.showGroupData(lab, tests);
    }

    //    public void RefeshPoints() {
//        mFSL_table.setPoints(getPoints());
////        mFSL_table.invalidate();
//    }
}
