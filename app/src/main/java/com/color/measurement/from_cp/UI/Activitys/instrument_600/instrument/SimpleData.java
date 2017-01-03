package com.color.measurement.from_cp.UI.Activitys.instrument_600.instrument;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import com.color.measurement.from_cp.Utils.ColorAssist.Contast1;
import com.color.measurement.from_cp.Utils.ColorUtils.ColorUtils;
import com.color.measurement.from_cp.Utils.TimeUtils;

/**
 * Created by wpc on 2016/12/20.
 */

public class SimpleData {

    String name, tips;
    String Data;
    String time;
    List mLab, XYZ;
    float[] SCI, SCE;//41  41  3
    boolean isStandard;
    boolean[] flags;

    boolean result;
    int color;

    public SimpleData(boolean isStandard, String name, float[] SCI, @Nullable Boolean hege) {
        this.isStandard = isStandard;
        this.name = name;
        this.SCI = SCI;
        XYZ = com.color.measurement.from_cp.Utils.color.comuteXYZ(SCI, Contast1.ILLUMINANT_D65, 0);
        mLab = com.color.measurement.from_cp.Utils.color.XYZtoHunterLab((double) XYZ.get(0), (double) XYZ.get(1), (double) XYZ.get(2), Contast1.ILLUMINANT_D65, 0);
        List c = com.color.measurement.from_cp.Utils.color.LabtoRGB((double) mLab.get(0), (double) mLab.get(1), (double) mLab.get(2));
        color = ColorUtils.getColor(c);
        if (hege != null) {
            result = hege;
        }
        time = TimeUtils.getHourMin();
        Log.i("simpledata_color", color + "");
    }

    public SimpleData(boolean isStandard, String name, float[] SCI, int color, List XYZ, List lab, @Nullable Boolean hege) {
        this.isStandard = isStandard;
        this.name = name;
        this.SCI = SCI;
        this.color = color;
        this.XYZ = XYZ;
        mLab = lab;
        if (hege != null) {
            result = hege;
        }
        time = TimeUtils.getHourMin();
        Log.i("simpledata_color", color + "");
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List getLab() {
        return mLab;
    }

    public void setLab(List lab) {
        mLab = lab;
    }

    public List getXYZ() {
        return XYZ;
    }

    public void setXYZ(List XYZ) {
        this.XYZ = XYZ;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public float[] getSCI() {
        return SCI;
    }

    public void setSCI(float[] SCI) {
        this.SCI = SCI;
    }

    public float[] getSCE() {
        return SCE;
    }

    public void setSCE(float[] SCE) {
        this.SCE = SCE;
    }


    public boolean isStandard() {
        return isStandard;
    }

    public void setStandard(boolean standard) {
        isStandard = standard;
    }

    public boolean[] getFlags() {
        return flags;
    }

    public void setFlags(boolean[] flags) {
        this.flags = flags;
    }

}