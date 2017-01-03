package com.color.measurement.from_cp.UI.fragments.dialog_fragment;

import java.util.ArrayList;

/**
 * Created by wpc on 2016/12/6.
 */

public interface DiscoverViewCallBack {
    void refeshListView(ArrayList<String> items);
    void setDescoveryState(boolean isDiscovery);
}
