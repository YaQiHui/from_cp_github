package com.color.measurement.from_cp.UI.Activitys.instrument_600.instrument;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.color.measurement.from_cp.R;

import java.util.ArrayList;

/**
 * Created by wpc on 2016/12/20.
 */

public class ExpandAdapter implements ExpandableListAdapter {

    ArrayList<GroupData> mGroupDatas;
    private LayoutInflater inflater;
    View.OnClickListener groupButtonClickListener;
    View.OnClickListener itemButtonClickListener;
    Integer groupIndex, itemPostion;

    ExpandAdapter(Context context, ArrayList<GroupData> mGroupDatas,
                  View.OnClickListener groupButtonClickListener,
                  View.OnClickListener itemButtonClickListener

            , @Nullable Integer groupIndex, @Nullable Integer itemPosition

    ) {
        inflater = LayoutInflater.from(context);
        this.mGroupDatas = mGroupDatas;
        this.groupButtonClickListener = groupButtonClickListener;
        this.itemButtonClickListener = itemButtonClickListener;
        this.groupIndex = groupIndex;
        this.itemPostion = itemPosition;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return mGroupDatas.size();
//        return mGroupDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mGroupDatas.size() == 0) {
            return 0;
        }
        if (mGroupDatas.get(groupPosition).getTest_data() != null) {
            return mGroupDatas.get(groupPosition).getTest_data().size();
        }
        return 0;
//        return mGroupDatas.get(groupPosition).getTest_data().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupDatas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroupDatas.get(groupPosition).getTest_data().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder gvh;
        if (convertView == null) {
            gvh = new GroupViewHolder();
            convertView = inflater.inflate(R.layout.instrument_epl_standard_item, parent, false);
            gvh.color = (Button) convertView.findViewById(R.id.color_spc_epl_standard);
            gvh.tips = (TextView) convertView.findViewById(R.id.tips_group_expend);
            gvh.time = (TextView) convertView.findViewById(R.id.time_group_expend);
            gvh.name = (TextView) convertView.findViewById(R.id.name_group_expend);

//            convertView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return true;
//                }
//            });
            convertView.setTag(gvh);
        } else {
            gvh = (GroupViewHolder) convertView.getTag();
        }
        if (groupIndex != null && groupIndex == groupPosition && itemPostion == null) {
            convertView.setBackgroundColor(Color.GRAY);
        }
        SimpleData stand = mGroupDatas.get(groupPosition).getStandard();
//        gvh.color.setOnClickListener(groupButtonClickListener);
        gvh.name.setText(stand.getName());
        if (stand.getTips() != null) gvh.tips.setText(stand.getTips());
        gvh.time.setText(stand.getTime());
        gvh.color.setTag(groupPosition);
        gvh.color.setBackgroundColor(stand.getColor());
        gvh.color.setOnClickListener(groupButtonClickListener);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.instrument_epl_test_item, parent, false);
//            vh.et=(EditText)convertView.findViewById(R.id.et_item_eplv);
            vh.time = (TextView) convertView.findViewById(R.id.time_item_expandlv);
            vh.name = (TextView) convertView.findViewById(R.id.name_item_expandlv);
            vh.tips = (TextView) convertView.findViewById(R.id.tips_item_expandlv);
            vh.result = (TextView) convertView.findViewById(R.id.result_item_expandlv);
            vh.color = (Button) convertView.findViewById(R.id.color_spc_epl_item);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        if (groupIndex != null && groupIndex == groupPosition && itemPostion != null && itemPostion == childPosition) {
            convertView.setBackgroundColor(Color.GRAY);
        }
        SimpleData item = mGroupDatas.get(groupPosition).getTest_data().get(childPosition);
        vh.result.setText(item.isResult() ? "合格" : "不合格");
        vh.name.setText(item.getName());
        vh.color.setTag(groupPosition + "_" + childPosition);
        vh.color.setBackgroundColor(item.getColor());
        vh.color.setOnClickListener(itemButtonClickListener);
        vh.time.setText(item.getName());
        if (item.getTips() != null) vh.tips.setText(item.getTips());
        vh.time.setText(item.getTime());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }


    class GroupViewHolder {
        TextView tips, name, time, rc, result;
        private Button color;
    }

    class ViewHolder {
        //        EditText et;
        TextView name, tips, time, rc, result;
        private Button color;
    }
}
