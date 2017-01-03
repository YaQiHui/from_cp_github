package com.color.measurement.from_cp.UI.Activitys.instrument_600.TableA;

import java.util.ArrayList;

/**
 * Created by wpc on 2016/12/16.
 */
public class Line {

    boolean connect;
    int point_color;
    int point_r;
    int line_color;
    int line_width;
    ArrayList<point> mPoints;

    public Line(boolean connect, int point_color, int point_r, int line_color, int line_width,   ArrayList<point>  Points) {
        this.connect = connect;
        this.point_color = point_color;
        this.point_r = point_r;
        this.line_color = line_color;
        this.line_width = line_width;
        mPoints = Points;
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    public int getPoint_color() {
        return point_color;
    }

    public void setPoint_color(int point_color) {
        this.point_color = point_color;
    }

    public int getPoint_r() {
        return point_r;
    }

    public void setPoint_r(int point_r) {
        this.point_r = point_r;
    }

    public int getLine_color() {
        return line_color;
    }

    public void setLine_color(int line_color) {
        this.line_color = line_color;
    }

    public int getLine_width() {
        return line_width;
    }

    public void setLine_width(int line_width) {
        this.line_width = line_width;
    }


    public ArrayList<point> getPoints() {
        return mPoints;
    }

    public void setPoints(ArrayList<point> points) {
        mPoints = points;
    }
}