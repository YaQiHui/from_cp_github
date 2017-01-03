package com.color.measurement.from_cp.UI.Activitys.instrument_600.instrument;

import java.util.ArrayList;

/**
 * Created by wpc on 2016/12/20.
 */

public class GroupData {


    SimpleData standard;
    ArrayList<SimpleData> test_data;


    public GroupData(SimpleData standard, ArrayList<SimpleData> test_data) {
        this.standard = standard;
        this.test_data = test_data;
    }

    public SimpleData getStandard() {
        return standard;
    }

    public void setStandard(SimpleData standard) {
        this.standard = standard;
    }

    public ArrayList<SimpleData> getTest_data() {
        return test_data;
    }

    public void setTest_data(ArrayList<SimpleData> test_data) {
        this.test_data = test_data;
    }


}
