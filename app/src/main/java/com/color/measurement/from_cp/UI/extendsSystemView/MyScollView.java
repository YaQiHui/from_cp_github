package com.color.measurement.from_cp.UI.extendsSystemView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by wpc on 2016/12/16.
 */

public class MyScollView extends ScrollView {

    public MyScollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
