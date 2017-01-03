package com.color.measurement.from_cp.db;

/**
 * Created by cimcenter on 2016/12/23.
 */

public class TabItemBean {
    private String tabName;
    private String date;
    private String time;
    private String name;
    private String remork;
    private double x;
    private double y;
    private double z;
    private int id;

    public TabItemBean(String tabName, String time, String date, String remork, String name, int id, double x, double y, double z) {
        this.tabName = tabName;
        this.id = id;
        this.z = z;
        this.y = y;
        this.x = x;
        this.remork = remork;
        this.name = name;
        this.time = time;
        this.date = date;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemork() {
        return remork;
    }

    public void setRemork(String remork) {
        this.remork = remork;
    }
}
