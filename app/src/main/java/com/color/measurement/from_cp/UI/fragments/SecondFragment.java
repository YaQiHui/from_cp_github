package com.color.measurement.from_cp.UI.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.color.measurement.from_cp.R;

import java.util.ArrayList;
import java.util.List;

import com.color.measurement.from_cp.bean.Consts;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class SecondFragment extends Fragment implements View.OnClickListener {

    private Button bt_conn;
    private LineChartView chart;
    private int maxNumberOfLines = 1;//可以存在几条线
    private int numberOfPoints = 30;

    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    //    private boolean hasLines = true;
//    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;

    static LineChartData data;
    private OnFragmentInteractionListener mListener;

    @Override
    public void onResume() {
        Log.e("fragment2", "onResume");
        generateDefaultData();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        view.findViewById(R.id.bt_showdialog_second).setOnClickListener(this);
        bt_conn = (Button) view.findViewById(R.id.bt_connection_fragment2);
        bt_conn.setOnClickListener(this);
        chart = (LineChartView) view.findViewById(R.id.chart_second);
        generateDefaultData();
        return view;
    }

    void generateDefaultData() {

        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 100f;
            }
        }

        List<Line> lines = new ArrayList<Line>();
        //最多4条线
        for (int i = 0; i < maxNumberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            //每条线30个点
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(Consts.chart_hasLines);
            line.setHasPoints(Consts.chart_hasPoint);
            if (pointsHaveDifferentColor) {
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        data = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_showdialog_second:
                ChartDetialFragment dialog = new ChartDetialFragment();
                dialog.show(getActivity().getFragmentManager(), "ChartDetialFragment");
//                LayoutInflater inflater=LayoutInflater.from(getActivity());
//                View layout=inflater.inflate(R.layout.dialog_fragment_chart,null);
//                AlertDialog.Builder bulider=new AlertDialog.Builder(getActivity());
//                bulider.setTitle("详细信息");
//                bulider.setView(layout);

                break;
            case R.id.bt_connection_fragment2:

                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
