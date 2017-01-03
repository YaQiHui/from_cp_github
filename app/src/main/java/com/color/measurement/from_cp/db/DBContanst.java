package com.color.measurement.from_cp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by cimcenter on 2016/12/24.
 */

public class DBContanst {

    public static final String CONFIG_NAME = "Config.db";//config库名
    public static final String CONFIG_TAB_NAME = "config";


    //config表
    public static final String CREATE_BOOK =
            "create table " + CONFIG_TAB_NAME + "("
                    + "id integer primary key autoincrement,"
                    + "mapped text,"
                    + "name text)";


    public static final String DATA_NAME = "Data.db";//datak库名

    //data表
    public static String createTableIfNotExist(String name) {
        return "create table if not exists " + name + "("
                + "id integer primary key autoincrement,"
                + "data text,"
                + "time text,"
                + "name text,"

                + "x double,"
                + "y double,"
                + "z double,"
                + "which int,"
                + "SCI1 double," + "SCI2 double," + "SCI3 double," + "SCI4 double," + "SCI5 double," + "SCI6 double," + "SCI7 double," + "SCI8 double," + "SCI9 double," + "SCI10 double,"
                + "SCI11 double," + "SCI12 double," + "SCI13 double," + "SCI14 double," + "SCI15 double," + "SCI16 double," + "SCI17 double," + "SCI18 double," + "SCI19 double," + "SCI20 double,"
                + "SCI21 double," + "SCI22 double," + "SCI23 double," + "SCI24 double," + "SCI25 double," + "SCI26 double," + "SCI27 double," + "SCI28 double," + "SCI29 double," + "SCI30 double,"
                + "SCI31 double," + "SCI32 double," + "SCI33 double," + "SCI34 double," + "SCI35 double," + "SCI36 double,"
                + "whichIorE int,"
                + "remark text,"
                + "color integer)";

    }

    public static Integer getMaxId(Context context) {
        MyDBHelper dbHelper = new MyDBHelper(context, DBContanst.CONFIG_NAME, null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(CONFIG_TAB_NAME, null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            return id;
        }
        return null;
    }

    public static String getMappedName(int id) {
        int h = id / 26 + 65;//A 65
        int l = id % 26 + 65;
        return (char) h + "_" + (char) l;
    }
}
