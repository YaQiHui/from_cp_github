package com.color.measurement.from_cp.UI.Activitys.instrument_600.TableB;

/**
 * Created by wpc on 2016/12/19.
 */

public class Lab{
    double L,a,b;

    public Lab(){

    }
    public Lab(double l, double a, double b) {
        L = l;
        this.a = a;
        this.b = b;
    }

    public double getL() {
        return L;
    }

    public void setL(double l) {
        L = l;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }
}