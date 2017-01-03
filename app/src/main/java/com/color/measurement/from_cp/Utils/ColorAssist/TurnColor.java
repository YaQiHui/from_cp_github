package com.color.measurement.from_cp.Utils.ColorAssist;

/**
 * RGB转换为颜色格式
 * Created by cimcenter on 2016/12/10.
 */

public class TurnColor {
    public static String toHex(int r, int g, int b) {
        return "#" + toBrowserHexValue(r) + toBrowserHexValue(g)
                + toBrowserHexValue(b);
    }
    private static String toBrowserHexValue(int number) {
        StringBuilder builder = new StringBuilder(
                Integer.toHexString(number & 0xff));
        while (builder.length() < 2) {
            builder.append("0");
        }
        return builder.toString().toUpperCase();
    }
}
