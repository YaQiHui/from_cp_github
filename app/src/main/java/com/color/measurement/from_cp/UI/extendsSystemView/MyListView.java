package com.color.measurement.from_cp.UI.extendsSystemView;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by wpc on 2016/12/16.
 * onTouch的时候让父布局不要拦截滑动事件
 */

public class MyListView extends ListView
{
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(android.content.Context context,android.util.AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        getParent().requestDisallowInterceptTouchEvent(true);//这句话的作用 告诉父view，我的单击事件我自行处理，不要阻碍我。
        return super.dispatchTouchEvent(ev);
    }
}