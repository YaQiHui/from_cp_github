package com.color.measurement.from_cp.UI.Activitys.instrument_600.TableB;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.color.measurement.from_cp.UI.Activitys.instrument_600.instrument.SimpleData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpc on 2016/12/19.
 */

public class Light_table extends View implements onRefeshListener {

    public Light_table(Context context) {
        super(context);
    }

    public Light_table(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Light_table(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    boolean isSC_mod = true;
    float minAbs = 10f;
    float y_from, y_to, y_density;
    int y_ = 5;
    int text_x, num_x, line_x, table_x;
    int x_paddingBottom, x_paddingTop;
    int chart_height;
    int width, height;
    Point start;
    Paint mPaint;

    Lab stand;
    ArrayList<Lab> test=new ArrayList<>();


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = canvas.getWidth();
        height = canvas.getHeight();
        if (!isSC_mod) {
            y_from = 0f;
            y_to = 100f;
        } else {
            float f = getMinAbs();
            y_from = -f;
            y_to = f;
        }
        line_x = width / 2;
        text_x = line_x / 5;
        num_x = line_x * 2 / 5;
        table_x = line_x / 2 * 3;

        x_paddingBottom = height / 5;
        x_paddingTop = x_paddingBottom / 2;

        chart_height = height - x_paddingBottom - x_paddingTop;
        y_density = chart_height / (y_to - y_from);

        start = new Point((line_x + table_x) / 2, (int) (height - x_paddingBottom - (0 - y_from) * y_density));


        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(20);

        Path text_path = new Path();
        text_path.moveTo(text_x, height - x_paddingBottom - 100);
        text_path.lineTo(text_x, height - x_paddingBottom - 360);

        canvas.drawTextOnPath((isSC_mod ? "Δ" : "") + "L*(D65)[<1>]", text_path, 0, 0, mPaint);


        canvas.drawText(y_from + "", num_x, height - x_paddingBottom + y_, mPaint);
        canvas.drawText((y_from + y_to) / 2 + "", num_x, height - x_paddingBottom - (chart_height / 2) + y_, mPaint);
        canvas.drawText(y_to + "", num_x, height - x_paddingBottom - chart_height + y_, mPaint);


        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        Path line_path = new Path();
        line_path.moveTo(line_x, height - x_paddingBottom);
        line_path.lineTo(line_x, x_paddingTop);


        canvas.drawPath(line_path, mPaint);

//        Rect rect=new Rect(table_x,x_paddingTop,table_x+30,height-x_paddingBottom);
//        canvas.drawRect(rect);

        Paint p = new Paint();
        LinearGradient lg = new LinearGradient(table_x, x_paddingTop, table_x, height - x_paddingBottom, Color.WHITE, Color.BLACK, Shader.TileMode.CLAMP);  //
        p.setShader(lg);
        canvas.drawRect(new Rect(table_x, x_paddingTop, table_x + 30, height - x_paddingBottom), p); //参数3为画圆的半径，类型为float型。

        mPaint.setStyle(Paint.Style.FILL);

        if (stand != null && test != null) {
            for (int i = 0; i < test.size(); i++) {
                canvas.drawCircle(start.x, (float) (start.y - (test.get(i).getL() - stand.getL()) * y_density), 3, mPaint);
            }
        }

    }

    private float getMinAbs() {
        if (stand != null & test != null) {
            for (int i = 1; i < 10; i++) {
                if (outBounds(i) && !outBounds(i + 1)) {
                    return (i + 1) * minAbs;
                }
            }
        }
        return 100f;
    }

    private boolean outBounds(int i) {
        for (Lab l : test) {
            if (Math.abs(l.getL() - stand.getL()) > i * minAbs) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clearLabs() {
        stand = null;
        test = new ArrayList<>();
    }

    @Override
    public void setLab(Lab lab, boolean isStand) {
        if (isStand) {
            stand = lab;
            test = new ArrayList<>();
        } else {
            if (test == null) {
                test = new ArrayList<>();
            }
            test.add(lab);
        }
        if (stand != null && test != null && test.size() != 0) {
            invalidate();
        }
    }

    public void setStandAndTest(Lab stand, Lab test) {
        this.stand = stand;
        this.test.clear();
        this.test.add(test);
        invalidate();
    }

    @Override
    public void showGroupData(List lab, @Nullable  ArrayList<SimpleData> tests) {
        this.stand = new Lab((double) lab.get(0),(double) lab.get(1),(double) lab.get(2));
        this.test.clear();
        for (int i = 0; i < tests.size(); i++) {
           List l= tests.get(i).getLab();
            this.test.add(new Lab((double) l.get(0),(double) l.get(1),(double) l.get(2)));
        }
        invalidate();
    }
}
