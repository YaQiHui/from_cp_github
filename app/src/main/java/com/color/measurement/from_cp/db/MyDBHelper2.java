package com.color.measurement.from_cp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by cimcenter on 2016/12/15.
 */

public class MyDBHelper2 extends SQLiteOpenHelper {
    private Context mConetext;

    public MyDBHelper2(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mConetext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public boolean deleteDatabase(Context context, String name) {
        return context.deleteDatabase(name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
