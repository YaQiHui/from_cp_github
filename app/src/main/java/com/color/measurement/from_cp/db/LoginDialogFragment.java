package com.color.measurement.from_cp.db;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.color.measurement.from_cp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import com.color.measurement.from_cp.Utils.color;

import com.color.measurement.from_cp.Utils.ColorAssist.T;


/**
 * Created by cimcenter on 2016/12/23.
 */

@SuppressLint("ValidFragment")
public class LoginDialogFragment extends DialogFragment {

    private EditText mUsername;
    Activity activity;
    private int id;
    private String name;
    private LocalBroadcastManager manager2;
    private Intent intentBoard2;
    private TextView textFirst;
    private ArrayList<Double> intArray;
    double x, y, z;
    private View view;
    private TextView textForth,textFive,textSix,textSeven,textEight;
    private TextView textSecond;
    private TextView textThird;
    private Spinner leftSpinner;
    private TextView text1, text2, text3,text4,text5,text6,text7,text8;
    private String str ="L*a*b*";
    private LinearLayout linearLayout,linearLayout2,linearLayout3,linearLayout4,linearLayout5;
    private Button button;
    private ArrayList<Integer> nameEx;
    private EditText mingEdit;

    private static String getDouble(double db) {
        DecimalFormat df = new DecimalFormat("######0.00"); //保留两位小数点
        String str2 = df.format(db);
        return str2;
    }

    private static String getfourDouble(double db) {
        DecimalFormat df = new DecimalFormat("######0.0000"); //保留两位小数点
        String str1 = df.format(db);
        return str1;
    }

    public LoginDialogFragment(Activity activity, int id, String name) {
        this.activity = activity;
        this.id = id;
        this.name = name;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MyDBHelper2 dbHelper2 = new MyDBHelper2(getActivity(), DBContanst.DATA_NAME, null, 1);
        final SQLiteDatabase db = dbHelper2.getWritableDatabase();
        intArray = new ArrayList<>();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.item_dialog_fragment, null);
        initFind();//查找控件
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //获取传过来的数据 并计算出结果
        nameEx = new ArrayList<>();
        if (name != null) {
            Log.e("main", "遍历id====" + id);
            Cursor cursor = db.query(name, null, "id = " + id + "", null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String nameP = cursor.getString(cursor.getColumnIndex("name"));
                    String red = cursor.getString(cursor.getColumnIndex("remark"));
                    int idt = cursor.getInt(cursor.getColumnIndex("id"));
                    x = cursor.getDouble(cursor.getColumnIndex("x"));
                    y = cursor.getDouble(cursor.getColumnIndex("y"));
                    z = cursor.getDouble(cursor.getColumnIndex("z"));
                    SPUtils.put(getActivity(), "id", idt + "");
                    SPUtils.put(getActivity(),"red",red);
                    mUsername.setText(red);
                    mingEdit.setText(nameP);
                    Log.e("main", "x==" + x);
                    Log.e("main", "y==" + y);
                    Log.e("main", "z==" + z);
                    intArray.add(x);
                    intArray.add( y);
                    intArray.add(z);
                    nameEx.add(idt);
                } while (cursor.moveToNext());
            }
        }
        //导出数据
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将id传过去
                SPUtils.put(getActivity(),"export",nameEx.get(0));
                //并跳转到另一个页面
                T.showShort(getActivity(),"跳转");
            }
        });
        builder.setView(view).setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id1) {
                        String re = mUsername.getText().toString();
                        String ming = mingEdit.getText().toString();
                        SPUtils.put(getActivity(), "re", re);
                        SPUtils.put(getActivity(), "ming", ming);
                        manager2 = LocalBroadcastManager.getInstance(getActivity());
                        intentBoard2 = new Intent("turnre");
                        manager2.sendBroadcast(intentBoard2);
                    }
                }).setNegativeButton("取消", null);
//        List list = color.XYZtoCIE_LabCH(intArray.get(0), intArray.get(1), intArray.get(2), 0x04, 0);
        //countData();
        return builder.create();
    }

    private void initFind() {
        mingEdit = (EditText) view.findViewById(R.id.id_txt_your_mingc);
        button = (Button) view.findViewById(R.id.bt_export);
        textFirst = (TextView) view.findViewById(R.id.et1_sp1t);
        textSecond = (TextView) view.findViewById(R.id.et2_sp1t);
        textThird = (TextView) view.findViewById(R.id.et3_sp1t);
        textForth = (TextView) view.findViewById(R.id.et4_sp1t);
        textFive = (TextView) view.findViewById(R.id.et5_sp1t);
        textSix = (TextView) view.findViewById(R.id.et6_sp1t);
        textSeven = (TextView) view.findViewById(R.id.et7_sp1t);
        textEight = (TextView) view.findViewById(R.id.et8_sp1t);
        mUsername = (EditText) view.findViewById(R.id.id_txt_your_name);
        leftSpinner = (Spinner) view.findViewById(R.id.spinner_leftt);
        text1 = (TextView) view.findViewById(R.id.left_text1);
        text2 = (TextView) view.findViewById(R.id.left_text2);
        text3 = (TextView) view.findViewById(R.id.left_text3);
        text4 = (TextView) view.findViewById(R.id.left_text4);
        text5 = (TextView) view.findViewById(R.id.left_text5);
        text6 = (TextView) view.findViewById(R.id.left_text6);
        text7 = (TextView) view.findViewById(R.id.left_text7);
        text8 = (TextView) view.findViewById(R.id.left_text8);
        linearLayout = (LinearLayout) view.findViewById(R.id.lin_test);
        linearLayout2 = (LinearLayout) view.findViewById(R.id.lin_test2);
        linearLayout3 = (LinearLayout) view.findViewById(R.id.lin_test3);
        linearLayout4 = (LinearLayout) view.findViewById(R.id.lin_test4);
        linearLayout5 = (LinearLayout) view.findViewById(R.id.lin_test5);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, GlobalBean.titlesNOLOGIN);
        leftSpinner.setAdapter(arrayAdapter);
        leftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str = parent.getItemAtPosition(position).toString();
                refreshLeftView(str);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void refreshLeftView(String str) {
        ArrayList<String> data = (ArrayList) (DBGlobalBean.datas.get(str));
        if(data.size()>4){
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);
            linearLayout3.setVisibility(View.VISIBLE);
            linearLayout4.setVisibility(View.VISIBLE);
            linearLayout5.setVisibility(View.VISIBLE);
            Log.e("main","data=="+data.size());

            text4.setText(data.get(4));
            text5.setText(data.get(5));
            text6.setText(data.get(6));
            text7.setText(data.get(7));
            text8.setText(data.get(8));
        }else {
            linearLayout.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.GONE);
            linearLayout3.setVisibility(View.GONE);
            linearLayout4.setVisibility(View.GONE);
            linearLayout5.setVisibility(View.GONE);
        }
        text1.setText(data.get(1));
        text2.setText(data.get(2));
        text3.setText(data.get(3));
        countData();

    }

    private void countData() {
        double x = intArray.get(0);
        double y = intArray.get(1);
        double z = intArray.get(2);
        List labList = color.XYZtoCIE_LabCH(x, y, z, 0x04, 0);
        String L = getDouble((double) labList.get(0));
        String a = getDouble((double) labList.get(1));
        String b = getDouble((double) labList.get(2));
        String CIE_C = getDouble((double) labList.get(3));
        String CIE_H = getDouble((double) labList.get(4));
        List rgbList = color.XYZtoRGB(x, y, z, 0x04, 0);
        int R = (int) (double) rgbList.get(0);
        int G = (int)(double) rgbList.get(1);
        int B = (int)(double) rgbList.get(2);

        List luvList = color.XYZtoLuv(x, y, z, 0x04, 0);
        String L1 = getDouble((double)luvList.get(0));
        String u = getDouble((double)luvList.get(1));
        String v = getDouble((double)luvList.get(2));

        List hunterList = color.XYZtoHunterLab(x, y, z, 0x04, 0);
        String HL = getDouble((double) hunterList.get(0));
        String HA= getDouble((double) hunterList.get(1));
        String HB = getDouble((double) hunterList.get(2));

        List YxyList = color.XYZtoYxy(x, y, z,0,0);
        String Y1 = getDouble((double) YxyList.get(0));
        String x1= getfourDouble((double) YxyList.get(1));
        String y1 = getfourDouble((double) YxyList.get(2));

        List MSE_HVCList = color.XYZtoMSE_HVC(x, y, z);
        String MSE_H = getDouble((double) MSE_HVCList.get(0));
        String MSE_V = getDouble((double) MSE_HVCList.get(1));
        String MSE_C = getDouble((double) MSE_HVCList.get(2));

        List TintList = color.XYZtoWI_TINT_CIE(x, y, z, 0x04, 0);
        String Tint1 = getDouble((double) TintList.get(0));
        String Tint2= getDouble((double) TintList.get(1));
        String Tint3 = getDouble((double) TintList.get(2));

        List YIList = color.XYZtoWI_TINT_CIE(x, y, z, 0x04, 0);//黄度
        String YI73 = getDouble((double) YIList.get(3));
        String Y110= getDouble((double) YIList.get(4));
        String YI1925 = getDouble((double) YIList.get(5));


        List YIbaiList = color.XYZtoWI_TINT_CIE(x, y, z, 0x04, 0);//白度
        String wi1 = getDouble((double) YIbaiList.get(6));
        String wi2 = getDouble((double) YIbaiList.get(7));
        String wi3 = getDouble((double) YIbaiList.get(8));
        String wi4 = getDouble((double) YIbaiList.get(9));
        String wi5 = getDouble((double) YIbaiList.get(10));
        String wi6 = getDouble((double) YIbaiList.get(11));
        String wi7 = getDouble((double) YIbaiList.get(12));
        String wi8 = getDouble((double) YIbaiList.get(13));
        Log.e("main","main str==="+str);
        switch (str){
            case "L*a*b*":
                textFirst.setText(L);
                textSecond.setText(a);
                textThird.setText(b);
                break;
            case "RGB":
                //RGB
                textFirst.setText(R+"");
                textSecond.setText(G+"");
                textThird.setText(B+"");
                break;
            case "L*u*v*":
                //Luv
                textFirst.setText(L1);
                textSecond.setText(u);
                textThird.setText(v);
                break;
            case "L*c*h":
                //LCH
                textFirst.setText(L);
                textSecond.setText(CIE_C);
                textThird.setText(CIE_H);
                break;
            case "Hunter Lab":
                //HunterLab
                textFirst.setText(HL);
                textSecond.setText(HA);
                textThird.setText(HB);
                break;
            case "Saybolt"://
                List list =color.XYZtoCIE_LabCH(x, y, z, 0x04, 0);
                double L3 = (double) list.get(0);
                double a3 = (double) list.get(0);
                double b3 = (double) list.get(0);
                double saybolt = color.getSaybolt(L3,a3,b3);
                textFirst.setText(getDouble(saybolt));
                textSecond.setText(getDouble(saybolt));
                textThird.setText(getDouble(saybolt));
                /*if(rightStr1.equals("2°")&&de==0x01){
                    double saybolt = Color.getSaybolt(L,a,b);
                    textFirst.setText(getDouble(saybolt));
                    textSecond.setText(getDouble(saybolt));
                    textThird.setText(getDouble(saybolt));
                }else {
                    tv1_result.setText("显示条件:C/2°");
                    tv2_result.setText("显示条件:C/2°");
                    tv3_result.setText("显示条件:C/2°");
                    textFirst.setText(getDouble(saybolt));
                    textSecond.setText(getDouble(saybolt));
                    textThird.setText(getDouble(saybolt));
                }*/
                //saybolt
                break;
            case "ASTM_Color"://???
                double STM_Color = color.getASTM_Color(x,y,z);
                textFirst.setText(getDouble(STM_Color));
                textSecond.setText(getDouble(STM_Color));
                textThird.setText(getDouble(STM_Color));
                break;
            case "Tint":
                textFirst.setText(Tint1);
                textSecond.setText(Tint2);
                textThird.setText(Tint3);
                break;
            case "黃度YI":
                textFirst.setText(YI73);
                textSecond.setText(Y110);
                textThird.setText(YI1925);
                break;
            case "白度WI"://???
                textFirst.setText(wi1);
                textSecond.setText(wi2);
                textThird.setText(wi3);
                textForth.setText(wi4);
                textFive.setText(wi5);
                textSix.setText(wi6);
                textSeven.setText(wi7);
                textEight.setText(wi8);

                break;

            case "Yxy":
                //Yxy
                textFirst.setText(Y1);
                textSecond.setText(x1);
                textThird.setText(y1);
                break;
            case "MSE_HVC":
                //MSE_HVC
                textFirst.setText(MSE_H);
                textSecond.setText(MSE_V);
                textThird.setText(MSE_C);
                break;

        }
        Log.e("main","L===="+L);
    }

}
