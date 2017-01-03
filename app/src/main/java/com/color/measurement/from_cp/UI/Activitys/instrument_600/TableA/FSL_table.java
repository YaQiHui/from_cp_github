package com.color.measurement.from_cp.UI.Activitys.instrument_600.TableA;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.color.measurement.from_cp.R;

import java.util.ArrayList;

/**
 * Created by wpc on 2016/12/5.
 */

public class FSL_table extends View implements PositionChangable {
    enum TestMod {
        SCI, SCE, SCI_SCE
    }

    private TestMod mTestMod = TestMod.SCI;
    int paddingleft = 40, paddingright = 100, paddingbottom = 60, paddingtop = 10;
    int tip_margin_table = 20, tips_margin_top = 30, tip_between = 50;
    int text_size = 24, tip_text_size = 24;
    int t_sci, s_sci, t_sce, s_sce;
    String unit;

    int width, height;

    Point startPoint;

    int x_begin = 400, y_begin = 0,
            x_end = 700, y_end = 100;

    int x_interval, y_interval;
    int x_min_num = 5, y_min_num = 10;

    private Paint mPaint;
    private ArrayList<Line> mLines;

    private OnLongClickListener mOnLongClickListener;

    @Override
    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }

    public FSL_table(Context context) {
        this(context, null);
    }

    public FSL_table(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FSL_table(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.FSL_table, 0, 0);
        unit = t.getString(R.styleable.FSL_table_unit);
        t_sci = t.getColor(R.styleable.FSL_table_t_sci_color, 0xff000000);
        s_sci = t.getColor(R.styleable.FSL_table_s_sci_color, 0xff000000);
        t_sce = t.getColor(R.styleable.FSL_table_t_sce_color, 0xff000000);
        s_sce = t.getColor(R.styleable.FSL_table_s_sce_color, 0xff000000);

        t.recycle();

    }

    int x_length, y_length, x_intervel_length, y_interval_length;
    float x_density, y_density;

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(0xff111111);
//        mPaint.setAntiAlias(false);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(5);

        width = canvas.getWidth();
        height = canvas.getHeight();

        Log.i("width_height", width + "_" + height);
        x_length = width - paddingleft - paddingright;
        y_length = height - paddingbottom - paddingtop;

        x_intervel_length = x_length / x_min_num;
        y_interval_length = y_length / y_min_num;

        x_interval = (x_end - x_begin) / x_min_num;
        y_interval = (y_end - y_begin) / y_min_num;

        x_density = x_intervel_length / (float) x_interval;
        y_density = y_interval_length / (float) y_interval;

        startPoint = new Point(paddingleft, height - paddingbottom);

//        canvas.drawCircle(100, 100, 90, mPaint);
//        Log.i("onDraw",startPoint.x+""+ (startPoint.y - y_min_num * y_interval_length)+""+startPoint.x+""+ startPoint.y);
//        canvas.drawLine( startPoint.x, startPoint.y - y_min_num * y_interval_length,startPoint.x, startPoint.y, mPaint);
//        canvas.drawLine( startPoint.x + x_min_num * x_intervel_length, startPoint.y,startPoint.x, startPoint.y, mPaint);

        drawTips(canvas);

        if (mLines != null) {
            for (Line l : mLines) {
                drawLine(canvas, l);
            }
        }

        mPaint.setColor(0xff000000);
        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.FILL);


        mPaint.setStyle(Paint.Style.FILL);
        drawXText(canvas);
        drawYText(canvas);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        drawXYLines(canvas);
        mPaint.setStrokeWidth(1);
        drawDotted(canvas);

    }


    float x, y;
    boolean hasClick;
    long downTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("onTouchEvent", "ACTION_DOWN" + event.getDownTime());
                hasClick = false;
                downTime = event.getDownTime();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("onTouchEvent", "ACTION_MOVE" + event.getEventTime());
                if (event.getEventTime() - downTime > 1000) {
                    if (hasClick != true) {
                        mOnLongClickListener.onLongClick(this);
                        hasClick = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i("onTouchEvent", "ACTION_UP");
                break;
            default:
                Log.i("onTouchEvent", event.getAction() + "");
                break;

        }
        return true;
    }

    private void drawLine(Canvas c, Line l) {
        ArrayList<Point> points = translatePoints(l.getPoints());
        Path path = new Path();

        mPaint.setColor(l.getPoint_color());
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            if (i == 0) {
                path.moveTo(p.x, p.y);
            } else {
                path.lineTo(p.x, p.y);
            }
            c.drawCircle(p.x, p.y, l.getPoint_r(), mPaint);
        }
        if (l.isConnect()) {
            mPaint.setColor(l.getLine_color());
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(l.getLine_width());
            c.drawPath(path, mPaint);
//            mPaint.setColor(l.getLine_color());
//            mPaint.setStrokeWidth(l.getLine_width());
//            for (int i = 0; i <(points.size()-1) ; i++) {
//                c.drawLine(points.get(i).x,points.get(i).y,points.get(i+1).x,points.get(i+1).y,mPaint);
//            }
        }
    }

    private void drawTips(Canvas c) {
        mPaint.setTextSize(tip_text_size);
        if (mTestMod == TestMod.SCI | mTestMod == TestMod.SCI_SCE) {
            mPaint.setColor(s_sci);
            c.drawText("--S-SCI", width - paddingright + tip_margin_table, tips_margin_top, mPaint);
            mPaint.setColor(t_sci);
            c.drawText("--T-SCI", width - paddingright + tip_margin_table, tips_margin_top + tip_between, mPaint);

        }

        if (mTestMod == TestMod.SCE | mTestMod == TestMod.SCI_SCE) {
            mPaint.setColor(s_sce);
            c.drawText("--S-SCE", width - paddingright + tip_margin_table, tips_margin_top + tip_between * 2, mPaint);
            mPaint.setColor(t_sce);
            c.drawText("--T-SCE", width - paddingright + tip_margin_table, tips_margin_top + tip_between * 3, mPaint);
        }


    }

    public void setUnit(String unit) {
        this.unit = unit;
        invalidate();
    }

    public void setTipsColors(int s_sci, int t_sci, int s_sce, int t_sce) {
        this.t_sci = t_sci;
        this.s_sci = s_sci;
        this.t_sce = t_sce;
        this.s_sce = s_sce;
        invalidate();
    }


    int text_x_x_excursion = 30, text_y_x_excursion = 30;
    int text_x_y_excursion = 30, text_y_y_excursion = 100;

    private void drawXText(Canvas canvas) {
        mPaint.setTextSize(text_size);
        for (int i = 0; i < x_min_num; i++) {
            canvas.drawText(400 + i * x_interval + unit, (startPoint.x - text_x_x_excursion + x_intervel_length * i), (height - paddingbottom + text_x_y_excursion), mPaint);
        }
    }

    private void drawYText(Canvas canvas) {
        for (int i = 0; i < y_min_num; i++) {
            canvas.drawText(0 + i * y_interval + "", (startPoint.x - text_y_x_excursion), (height - paddingbottom - y_interval_length * i), mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    void drawXYLines(Canvas c) {
        c.drawLine(startPoint.x, startPoint.y, startPoint.x, startPoint.y - y_length, mPaint);
        c.drawLine(startPoint.x, startPoint.y, startPoint.x + x_length, startPoint.y, mPaint);
    }

    void drawDotted(Canvas c) {
        Path path = new Path();
        addXpath(path);
        addYpath(path);
        PathEffect effects = new DashPathEffect(new float[]{5, 5}, 1);
        mPaint.setPathEffect(effects);
//        c.drawLine(0, 40, 500, 40, mPaint);
        c.drawPath(path, mPaint);
    }


    private void addXpath(Path path) {
        for (int i = 1; i < y_min_num; i++) {
            path.moveTo(startPoint.x, startPoint.y - y_interval_length * i);
            path.lineTo(startPoint.x + x_length, startPoint.y - y_interval_length * i);
        }
    }

    private void addYpath(Path path) {
        for (int i = 1; i < x_min_num; i++) {
            path.moveTo(startPoint.x + x_intervel_length * i, startPoint.y);
            path.lineTo(startPoint.x + x_intervel_length * i, startPoint.y - y_length);
        }
    }

//    public void setPoints(ArrayList<point> dataPoints) {
//        ArrayList<Point> translate_points = translatePoints(dataPoints);
//        Log.i("setPoints", dataPoints.toString());
//        for (int i = 0; i < translate_points.size(); i++) {
//            Point p = translate_points.get(i);
//            Log.i("p", p.toString());
//        }
//    }

    public void setLines(ArrayList<Line> lines) {
        this.mLines = lines;
        invalidate();
    }

    private ArrayList<Point> translatePoints(ArrayList<point> dataPoints) {
        ArrayList<Point> tran = new ArrayList<>();
        for (point p : dataPoints) {
            tran.add(translatePosition(p));
        }
        return tran;
    }


    public int getX_end() {
        return x_end;
    }

    public int getX_begin() {
        return x_begin;
    }

    @Override
    public Point translatePosition(point p) {
        return new Point((int) (startPoint.x + (p.x - 400) * x_density), (int) (startPoint.y - (p.y - y_begin) * y_density));
    }

    public void setMod(String mod) {
        if (mod.equals("SCI")) {
            mTestMod = TestMod.SCI;
        } else if (mod.equals("SCE")) {
            mTestMod = TestMod.SCE;
        } else {
            mTestMod = TestMod.SCI_SCE;
        }
        invalidate();
    }
}
