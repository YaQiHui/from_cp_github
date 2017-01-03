package com.color.measurement.from_cp.UI.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.color.measurement.from_cp.R;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PreviewLineChartView;

/**
 * Created by wpc on 2016/9/18.
 */
public class ChartDetialFragment extends DialogFragment {

    private LineChartView chart;
    private PreviewLineChartView previewChart;
    private TextView tv;
    private LineChartData previewData;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_chart, null);
        chart = (LineChartView) view.findViewById(R.id.chart_dialog);
        previewChart = (PreviewLineChartView) view.findViewById(R.id.chart_preview_dialog);
        tv=(TextView)view.findViewById(R.id.tv_chartdetial_dialog);

        generateDefaultData();

        chart.setLineChartData(SecondFragment.data);

        chart.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                tv.setText(value.toString());
            }

            @Override
            public void onValueDeselected() {

            }
        });
        chart.setZoomEnabled(false);
        chart.setScrollEnabled(false);

        previewChart.setLineChartData(previewData);
        previewChart.setViewportChangeListener(new ViewportListener());

        previewX(false);

        builder.setCancelable(false);
        builder.setTitle("ChartDetials").setView(view).
                setNegativeButton("no", null).
                setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return builder.create();
    }

//
//    private void generateValues() {
//        for (int i = 0; i < maxNumberOfLines; ++i) {
//            for (int j = 0; j < numberOfPoints; ++j) {
//                randomNumbersTab[i][j] = (float) Math.random() * 100f;
//            }
//        }
//    }
    void generateDefaultData(){

//        int numValues = 24;
//        List<PointValue> values = new ArrayList<PointValue>();
//        for (int i = 0; i < numValues; ++i) {
//            values.add(new PointValue(i, (float) Math.random() * 100f));
//        }
//
//        Line line = new Line(values);
//        line.setColor(ChartUtils.COLOR_GREEN);
//        if(numValues>30){
//            line.setHasPoints(false);// too many values so don't draw points.
//        }
//
//        List<Line> lines = new ArrayList<Line>();
//        lines.add(line);
//
//        data = new LineChartData(lines);
//        data.setAxisXBottom(new Axis());
//        data.setAxisYLeft(new Axis().setHasLines(true));
//
//        // prepare preview data, is better to use separate deep copy for preview chart.
//        // Set color to grey to make preview area more visible.
        previewData = new LineChartData(SecondFragment.data);
        previewData.getLines().get(0).setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
    }
    private void previewX(boolean animate) {
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        float dx = tempViewport.width() / 4;
        tempViewport.inset(dx, 0);
        if (animate) {
            previewChart.setCurrentViewportWithAnimation(tempViewport);
        } else {
            previewChart.setCurrentViewport(tempViewport);
        }
        previewChart.setZoomType(ZoomType.HORIZONTAL);
    }
    private void previewXY() {
        // Better to not modify viewport of any chart directly so create a copy.
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        // Make temp viewport smaller.
        float dx = tempViewport.width() / 4;
        float dy = tempViewport.height() / 4;
        tempViewport.inset(dx, dy);
        previewChart.setCurrentViewportWithAnimation(tempViewport);
    }

    private class ViewportListener implements ViewportChangeListener {

        @Override
        public void onViewportChanged(Viewport newViewport) {
            // don't use animation, it is unnecessary when using preview chart.
            chart.setCurrentViewport(newViewport);
        }

    }
}