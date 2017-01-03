package com.color.measurement.from_cp.UI.extendsSystemView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by wpc on 2016/12/16.
 * 在scollview中自动展开的listview
 * **未使用**
 * <p>
 *     需要手动返回scollview顶部
 * sv = (ScrollView) findViewById(R.id.act_solution_4_sv);
 * sv.smoothScrollTo(0, 0);
 */

public class ListViewForScrollView extends ListView {
    public ListViewForScrollView(Context context) {
        super(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
