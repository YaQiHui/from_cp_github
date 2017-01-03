package com.color.measurement.from_cp.UI.Activitys.instrument_600.TableC;

/**
 * Created by wpc on 2016/12/26.
 */

public class RCSet {
    String name;
    float rc;

    public RCSet(String name, float rc) {
        this.name = name;
        this.rc = rc;
    }

    public float getRc() {
        return rc;
    }

    public void setRc(float rc) {
        this.rc = rc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name+"_"+rc;
    }
}