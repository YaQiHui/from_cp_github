package com.color.measurement.from_cp.UI.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wpc on 2016/12/8.
 */

public class MyChart extends View {

    private Paint mPaint;

    public MyChart(Context context) {
        super(context);
    }

    public MyChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private static final String myString1 = "2006-2011上半年中国移动互联网行业各年度投资情况";
    private static final String myString2 = "来源：清科研究中心 2011.08";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint=new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(18);
        canvas.drawText(myString1,20,20,mPaint);
        canvas.drawLine(50,100,50,500,mPaint);
        canvas.drawLine(50,500,400,500,mPaint);
        int[] array1 = new int[]{0, 50, 100, 150, 200, 250, 300, 350};
        mPaint.setTextSize(10);//设置文字大小
        canvas.drawText("单位：百万美元", 20, 90, mPaint);
        for (int i = 0; i < array1.length; i++) {
            canvas.drawLine(50, 500 - array1[i], 54, 500 - array1[i], mPaint);
            canvas.drawText(array1[i] + "", 20, 500 - array1[i], mPaint);
        }
        //绘制横坐标文字
        String[] array2 = new String[]{"2008年", "2009年", "2010年", "2011上半年"};
        for (int i = 0; i < array2.length; i++) {
            canvas.drawText(array2[i], array1[i] + 80, 520, mPaint);
        }
    }
}
