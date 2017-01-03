package com.color.measurement.from_cp.UI.Activitys.instrument_600.TableC;

import com.color.measurement.from_cp.UI.Activitys.instrument_600.TableB.Lab;

/**
 * Created by wpc on 2016/12/21.
 */

public class TableData {
    Lab stand, test;
    private static TableData instance;

    private TableData() {
        stand = new Lab();
        test = new Lab();
    }

    public static TableData getInstance() {
        if (instance == null) {
            instance = new TableData();
        }
        return instance;
    }


    public Lab getTest() {
        return test;
    }

    public void setTest(Lab test) {
        this.test = test;
    }

    public Lab getStand() {
        return stand;
    }

    public void setStand(Lab stand) {
        this.stand = stand;
    }
}
