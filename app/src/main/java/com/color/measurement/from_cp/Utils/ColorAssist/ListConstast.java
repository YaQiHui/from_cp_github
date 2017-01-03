package com.color.measurement.from_cp.Utils.ColorAssist;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cimcenter on 2016/12/16.
 */

public class ListConstast {
    public static boolean IS_SHOW =false;

    public static  boolean IS_DOUBLE =false;

    public static  boolean IS_ALL_CHECKED =false;
    public  static double[] sci ={63.16,71.12,73.21,73.96,
            74.83,75.42,76.74,77.87,78.66,79.11,
            79.41,79.59,79.71,79.83,79.73,79.55,
            79.47,79.31,79.54,79.72,79.87,79.90,
            79.86,79.75,79.63,79.77,79.89,79.88,
            79.81,82.00,81.69};
    public static HashMap<String, ArrayList<String>> datas = initData();
    static HashMap initData(){
        HashMap<String, ArrayList<Double>> data = new HashMap<>();
        ArrayList<Double> list = new ArrayList<>();
        list.add(63.16);
        list.add(71.12);
        list.add(73.21);
        data.put("1",list);


        return data;
    }






}
