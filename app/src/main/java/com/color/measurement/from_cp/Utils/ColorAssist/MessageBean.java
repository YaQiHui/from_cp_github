package com.color.measurement.from_cp.Utils.ColorAssist;

import java.util.ArrayList;

/**
 * Created by cimcenter on 2016/12/17.
 */

public class MessageBean {

    private String data;
    private String time;
    private String name;
    private String remark;
    private int color;
    private double lab;

    /*public MessageBean(String data, String time,
                       String name, String remark,
                       int color) {
        this.data = data;
        this.time = time;
        this.name = name;
        this.remark = remark;
        this.color = color;
        this.lab = lab;
    }

    public  MessageBean(double lab){
        this.lab = lab;
    }*/

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getLab() {
        return lab;
    }

    public void setLab(double lab) {
        this.lab = lab;
    }

    public ArrayList<String> getArray(){
        ArrayList<String> list = new ArrayList<>();
        list.add(getData());
        list.add(getTime());
        list.add(getName());
        list.add(getRemark());
        list.add(getColor()+"");
        list.add(getLab()+"");
        return list;
    }
}
