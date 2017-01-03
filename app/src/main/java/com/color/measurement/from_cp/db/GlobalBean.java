package com.color.measurement.from_cp.db;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gzw on 2016/8/3.
 */
public class GlobalBean {
    public  static boolean isLogin=false;
    public static String titles[] = { "XYZ","反射率","Yxy","L*a*b*"};
    public static String titlesNOLOGIN[] = {"L*a*b*", "RGB", "XYZ","L*u*v*","L*c*h","Hunter Lab",
            "Yxy","Saybolt","ASTM_Color","Tint","黃度YI","白度WI","密度A","密度C","密度T","密度E","ISO2470","MSE_HVC"};
    public static  String light[] ={"A","C","D55","D55","D65","D75","F1","F2","F3",
            "F4","F5","F6","F7","F8","F9","F10","F11","F12","CWF","U30","DLF","NBF",
            "TL83","Tl84","U35"};
    public static  String Degree_mode[] ={"2°","10°"};
    public static HashMap<String, ArrayList<String>> datas = initData();

    static HashMap initData() {
        HashMap<String, ArrayList<String>> data = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        list.add("L*a*b*");
        list.add("L*:");
        list.add("a*:");
        list.add("b*:");
        data.put("L*a*b*",list);

        list = new ArrayList<>();
        list.add("L*c*h");
        list.add("L*:");
        list.add("c*:");
        list.add("h:");
        data.put("L*c*h",list);

        list = new ArrayList<>();
        list.add("MSE_HVC");
        list.add("H(Munsell):");
        list.add("V(Munsell):");
        list.add("C(Munsell):");
        data.put("MSE_HVC",list);

        list = new ArrayList<>();
        list.add("Tint");
        list.add("Tint(CIE):");
        list.add("Tint(E313):");
        list.add("Tint(Ganz):");
        data.put("Tint",list);

        list = new ArrayList<>();
        list.add("ISO2470");
        list.add("ISO2470:");
        list.add("ISO2470:");
        list.add("ISO2470:");
        data.put("ISO2470",list);

        list = new ArrayList<>();
        list.add("密度A");
        list.add("DENSITY(A) C:");
        list.add("DENSITY(A) M:");
        list.add("DENSITY(A) Y:");
        data.put("密度A",list);

        list = new ArrayList<>();
        list.add("密度T");
        list.add("DENSITY(T) C:");
        list.add("DENSITY(T) M:");
        list.add("DENSITY(T) Y:");
        data.put("密度T",list);

        list = new ArrayList<>();
        list.add("密度C");
        list.add("DENSITY(C) C:");
        list.add("DENSITY(C) M:");
        list.add("DENSITY(C) Y:");
        data.put("密度C",list);

        list = new ArrayList<>();
        list.add("密度E");
        list.add("DENSITY(E) C:");
        list.add("DENSITY(E) M:");
        list.add("DENSITY(E) Y:");
        data.put("密度E",list);

        list = new ArrayList<>();
        list.add("反射率");
        list.add(" ");
        list.add(" ");
        list.add(" ");
        data.put("反射率",list);

        list = new ArrayList<>();
        list.add("黃度YI");
        list.add("YI(ASM E313-73):");
        list.add("YI(ASM E313-10):");
        list.add("YI(D 1925):");
        data.put("黃度YI",list);

        list = new ArrayList<>();
        list.add("白度WI");
        list.add("");
        list.add("");
        list.add("");
        data.put("白度WI",list);

        list = new ArrayList<>();
        list.add("Yxy");
        list.add("Y:");
        list.add("x:");
        list.add("y:");
        data.put("Yxy",list);

        list = new ArrayList<>();
        list.add("Saybolt");
        list.add("Saybolt:");
        list.add("Saybolt:");
        list.add("Saybolt:");
        data.put("Saybolt",list);

        list = new ArrayList<>();
        list.add("ASTM_Color");
        list.add("ASTM_Color:");
        list.add("ASTM_Color:");
        list.add("ASTM_Color:");
        data.put("ASTM_Color",list);

        list = new ArrayList<>();
        list.add("RGB");
        list.add("R:");
        list.add("G:");
        list.add("B:");
        data.put("RGB",list);

        list = new ArrayList<>();
        list.add("Hunter Lab");
        list.add("Hunter L:");
        list.add("Hunter a:");
        list.add("Hunter b:");
        data.put("Hunter Lab",list);

        list = new ArrayList<>();
        list.add("XYZ");
        list.add("X:");
        list.add("Y:");
        list.add("Z:");
        data.put("XYZ",list);

        list = new ArrayList<>();
        list.add("L*u*v*");
        list.add("L*:");
        list.add("u*:");
        list.add("v*:");
        data.put("L*u*v*",list);

        list = new ArrayList<>();
        list.add("2°");
        data.put("2°",list);

        list = new ArrayList<>();
        list.add("10°");
        data.put("10°",list);

        list = new ArrayList<>();
        list.add("D50");
        data.put("D50",list);

        list = new ArrayList<>();
        list.add("A");
        data.put("A",list);

        list = new ArrayList<>();
        list.add("B");
        data.put("B",list);

        list = new ArrayList<>();
        list.add("C");
        data.put("C",list);

        list = new ArrayList<>();
        list.add("D55");
        data.put("D55",list);

        return data;
    }
}
