package com.color.measurement.from_cp.Utils.ColorUtils;


import android.graphics.Color;
import android.util.Log;

import java.util.List;

/**
 * Created by wpc on 2016/9/18.
 */
public class ColorUtils {

    private static List<Double> sDatas;

    //0~256
    public static int getAp(int color){
        return color&0xff000000;
    }
    public static int getRed(int color){
        return (color&0x00ff0000)>>>16;
    }
    public static int getGreen(int color){
        return (color&0x0000ff00)>>>8;
    }
    public static int getBlue(int color){
        return color&0x000000ff;
    }

//    public static int getColor(List<Double> datas){
//       int r=datas.get(0).intValue();
//        int g=datas.get(0).intValue();
//        int b=datas.get(0).intValue();
//        Log.i("ColorUtils","getColor"+r+"_"+g+"_"+b+"_");
//        return 0xff000000+(r<<16)+(g<<8)+b;
//    }

    public static int getColor(List<Double> datas){
        int r=datas.get(0).intValue();
        int g=datas.get(0).intValue();
        int b=datas.get(0).intValue();
        Log.i("ColorUtils","getColor"+r+"_"+g+"_"+b+"_");
        return  Color.rgb(r,g,b);
    }
}
