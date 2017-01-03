package com.color.measurement.from_cp.UI.Activitys.instrument_600.TableB;

import com.color.measurement.from_cp.UI.Activitys.instrument_600.instrument.SimpleData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpc on 2016/12/22.
 */

public interface onRefeshListener {

    public void clearLabs();
    public void setLab(Lab lab, boolean isStand);

    public void setStandAndTest(Lab stand, Lab test);

    public void showGroupData(List lab, ArrayList<SimpleData> tests);
}
