package com.color.measurement.from_cp.UI.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.Activitys.RegisterActivity;
import com.color.measurement.from_cp.UI.app;
import com.color.measurement.from_cp.Utils.ColorAssist.T;
import com.color.measurement.from_cp.Utils.ColorAssist.TurnColor;
import com.color.measurement.from_cp.Utils.ColorUtils.ColorUtils;
import com.color.measurement.from_cp.Utils.color;
import com.color.measurement.from_cp.db.GlobalBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FirstFragment extends Fragment implements View.OnClickListener {

    private Spinner left, right, left1, right1;
    private Button bt_regester;
    private Button bt_colorpicker;
    private Button changeBt, refButton;
    private TextView tv,
            tv1, tv2, tv3, tv4,
            tv5, tv6, tv7, tv8,
            tv4_result, tv3_result, tv2_result, tv1_result;
    private EditText et1, et2, et3, et4;
    String str1;
    String str2;
    String str3;
    String editStr1, editStr2, editStr3, editStr4, editStr5, editStr6, editStr7, editStr8,
            editStr9, editStr10, editStr11, editStr12, editStr13, editStr14, editStr15, editStr16,
            editStr17, editStr18, editStr19, editStr20, editStr21, editStr22, editStr23, editStr24,
            editStr25, editStr26, editStr27, editStr28, editStr29, editStr30, editStr31;
    private String leftStr;
    private String rightStr;
    private String leftStr1;
    private String rightStr1;
    private int degree_mode;
    private int m_nGyType;
    private PopupWindow popupWindow;
    private TextView wi_text1;
    private TextView vi_text2;
    private TextView vi_text3;
    private TextView vi_text4;
    private TextView vi_text5;
    private TextView vi_text6;
    private TextView vi_text7;
    private TextView vi_text8;
    private View viewDialog;
    private EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7,
            editText8, editText9, editText10, editText11, editText12, editText13, editText14, editText15,
            editText16, editText17, editText18, editText19, editText20, editText21, editText22, editText23, editText24,
            editText25, editText26, editText27, editText28, editText29, editText30, editText31;
    private View view;
    private double nm1, nm2, nm3, nm4, nm5, nm6, nm7, nm8, nm9, nm10, nm11, nm12, nm13, nm14, nm15,
            nm16, nm17, nm18, nm19, nm20, nm21, nm22, nm23, nm24, nm25, nm26, nm27, nm28, nm29, nm30, nm31;
    private double[] nm;
    private double iso2470;
    private AlertDialog alertDialog;


    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //tv.setText("照明：" + Consts.str_zm_value + "  观察：" + Consts.str_gc_value);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        bt_regester = (Button) view.findViewById(R.id.bt_register);
        changeBt = (Button) view.findViewById(R.id.button);
        bt_colorpicker = (Button) view.findViewById(R.id.colorpicker_first);
        tv = (TextView) view.findViewById(R.id.tv_fragment1);
        refButton = (Button) view.findViewById(R.id.tip_ref);
        tv1 = (TextView) view.findViewById(R.id.tv_sp1);//X
        tv2 = (TextView) view.findViewById(R.id.tv2_sp1);
        tv3 = (TextView) view.findViewById(R.id.tv3_sp1);
        tv4 = (TextView) view.findViewById(R.id.tv4_sp1);
        tv5 = (TextView) view.findViewById(R.id.tv5_sp2);
        tv6 = (TextView) view.findViewById(R.id.tv6_sp2);
        tv7 = (TextView) view.findViewById(R.id.tv7_sp2);
        tv8 = (TextView) view.findViewById(R.id.tv8_sp2);
        et1 = (EditText) view.findViewById(R.id.et1_sp1);
        et2 = (EditText) view.findViewById(R.id.et2_sp1);
        et3 = (EditText) view.findViewById(R.id.et3_sp1);
        et4 = (EditText) view.findViewById(R.id.et4_sp1);
        tv4_result = (TextView) view.findViewById(R.id.tv4_result);
        tv1_result = (TextView) view.findViewById(R.id.tv1_result);
        tv2_result = (TextView) view.findViewById(R.id.tv2_result);
        tv3_result = (TextView) view.findViewById(R.id.tv3_result);
        left = (Spinner) view.findViewById(R.id.spinner_left);
        right = (Spinner) view.findViewById(R.id.spinner_right);
        left1 = (Spinner) view.findViewById(R.id.spinner_left1);
        right1 = (Spinner) view.findViewById(R.id.spinner_right1);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, GlobalBean.titles);

        left.setAdapter(adapter);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, GlobalBean.titlesNOLOGIN);
        right.setAdapter(adapter);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, GlobalBean.light);
        left1.setAdapter(adapter);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, GlobalBean.Degree_mode);
        right1.setAdapter(adapter);
        et1.addTextChangedListener(textWatcher);
        et2.addTextChangedListener(textWatcher);
        et3.addTextChangedListener(textWatcher);
        Log.e("main", "editText1===" + editText1);
        Log.e("main", "viewDialog===" + viewDialog);
        initView();

        left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                leftStr = adapterView.getItemAtPosition(i).toString();
                refreshLeftView(leftStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        left1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                leftStr1 = adapterView.getItemAtPosition(i).toString();
                tv.setText("照明：" + leftStr1 + "  观察：" + rightStr1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        right1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rightStr1 = adapterView.getItemAtPosition(i).toString();
                refreshRight1View(rightStr1);
                tv.setText("照明：" + leftStr1 + "  观察：" + rightStr1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        right.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rightStr = adapterView.getItemAtPosition(i).toString();
                refreshRightView(rightStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        bt_regester.setOnClickListener(this);
        bt_colorpicker.setOnClickListener(this);
        changeBt.setOnClickListener(this);
        refButton.setOnClickListener(this);

        return view;
    }

    private void initView() {

    }

    private String getDouble(double db) {
        DecimalFormat df = new DecimalFormat("######0.00"); //保留两位小数点
        String str = df.format(db);
        return str;
    }

    private String getfourDouble(double db) {
        DecimalFormat df = new DecimalFormat("######0.0000"); //保留两位小数点
        String str = df.format(db);
        return str;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            str1 = et1.getText().toString();
            str2 = et2.getText().toString();
            str3 = et3.getText().toString();
            if ((editStr1 == null || editStr2 == null || editStr3 == null || editStr4 == null || editStr5 == null || editStr6 == null ||
                    editStr7 == null || editStr8 == null || editStr9 == null || editStr10 == null || editStr11 == null || editStr12 == null ||
                    editStr13 == null || editStr14 == null || editStr15 == null || editStr16 == null || editStr17 == null || editStr18 == null ||
                    editStr19 == null || editStr20 == null || editStr21 == null || editStr22 == null || editStr23 == null || editStr24 == null ||
                    editStr25 == null || editStr26 == null || editStr27 == null || editStr28 == null || editStr29 == null || editStr30 == null ||
                    editStr31 == null || editStr1.equals("") || editStr2.equals("") || editStr3.equals("") || editStr4.equals("")
                    || editStr5.equals("") || editStr6.equals("") || editStr7.equals("") || editStr8.equals("") || editStr9.equals("")
                    || editStr10.equals("") || editStr11.equals("") || editStr12.equals("") || editStr13.equals("") || editStr14.equals("")
                    || editStr15.equals("") || editStr16.equals("") || editStr17.equals("") || editStr18.equals("") || editStr19.equals("")
                    || editStr20.equals("") || editStr21.equals("") || editStr22.equals("") || editStr23.equals("") || editStr24.equals("")
                    || editStr25.equals("") || editStr26.equals("") || editStr27.equals("") || editStr28.equals("") || editStr29.equals("")
                    || editStr30.equals("") || editStr31.equals(""))) {
                return;
            }
            Log.e("main", "editText1.getText().toString()===" + editText1.getText().toString());

        }
    };

    void refreshLeftView(String str) {
        Log.e("main", "refreshLeftView====");
        Log.e("main", "shifou====" + (leftStr.equals("反射率") || leftStr.equals("反射率SCE")));
        if (leftStr.equals("反射率") || leftStr.equals("反射率SCE")) {
            Log.e("main", "DialogUtils====");
            refButton.setVisibility(View.VISIBLE);
            et1.setVisibility(View.GONE);
            et2.setVisibility(View.INVISIBLE);
            et3.setVisibility(View.INVISIBLE);
            viewDialog = LayoutInflater.from(getActivity()).inflate(R.layout.sci_dialog, null);
            editText1 = (EditText) viewDialog.findViewById(R.id.edittest1);
            editText2 = (EditText) viewDialog.findViewById(R.id.edittest2);
            editText3 = (EditText) viewDialog.findViewById(R.id.edittest3);
            editText4 = (EditText) viewDialog.findViewById(R.id.edittest4);
            editText5 = (EditText) viewDialog.findViewById(R.id.edittest5);
            editText6 = (EditText) viewDialog.findViewById(R.id.edittest6);
            editText7 = (EditText) viewDialog.findViewById(R.id.edittest7);
            editText8 = (EditText) viewDialog.findViewById(R.id.edittest8);
            editText9 = (EditText) viewDialog.findViewById(R.id.edittest9);
            editText10 = (EditText) viewDialog.findViewById(R.id.edittest10);
            editText11 = (EditText) viewDialog.findViewById(R.id.edittest11);
            editText12 = (EditText) viewDialog.findViewById(R.id.edittest12);
            editText13 = (EditText) viewDialog.findViewById(R.id.edittest13);
            editText14 = (EditText) viewDialog.findViewById(R.id.edittest14);
            editText15 = (EditText) viewDialog.findViewById(R.id.edittest15);
            editText16 = (EditText) viewDialog.findViewById(R.id.edittest16);
            editText17 = (EditText) viewDialog.findViewById(R.id.edittest17);
            editText18 = (EditText) viewDialog.findViewById(R.id.edittest18);
            editText19 = (EditText) viewDialog.findViewById(R.id.edittest19);
            editText20 = (EditText) viewDialog.findViewById(R.id.edittest20);
            editText21 = (EditText) viewDialog.findViewById(R.id.edittest21);
            editText22 = (EditText) viewDialog.findViewById(R.id.edittest22);
            editText23 = (EditText) viewDialog.findViewById(R.id.edittest23);
            editText24 = (EditText) viewDialog.findViewById(R.id.edittest24);
            editText25 = (EditText) viewDialog.findViewById(R.id.edittest25);
            editText26 = (EditText) viewDialog.findViewById(R.id.edittest26);
            editText27 = (EditText) viewDialog.findViewById(R.id.edittest27);
            editText28 = (EditText) viewDialog.findViewById(R.id.edittest28);
            editText29 = (EditText) viewDialog.findViewById(R.id.edittest29);
            editText30 = (EditText) viewDialog.findViewById(R.id.edittest30);
            editText31 = (EditText) viewDialog.findViewById(R.id.edittest31);
            editText1.addTextChangedListener(textWatcher);
            editText2.addTextChangedListener(textWatcher);
            editText3.addTextChangedListener(textWatcher);
            editText4.addTextChangedListener(textWatcher);
            editText5.addTextChangedListener(textWatcher);
            editText6.addTextChangedListener(textWatcher);
            editText7.addTextChangedListener(textWatcher);
            editText8.addTextChangedListener(textWatcher);
            editText9.addTextChangedListener(textWatcher);
            editText10.addTextChangedListener(textWatcher);
            editText11.addTextChangedListener(textWatcher);
            editText12.addTextChangedListener(textWatcher);
            editText13.addTextChangedListener(textWatcher);
            editText14.addTextChangedListener(textWatcher);
            editText15.addTextChangedListener(textWatcher);
            editText16.addTextChangedListener(textWatcher);
            editText17.addTextChangedListener(textWatcher);
            editText18.addTextChangedListener(textWatcher);
            editText19.addTextChangedListener(textWatcher);
            editText20.addTextChangedListener(textWatcher);
            editText21.addTextChangedListener(textWatcher);
            editText22.addTextChangedListener(textWatcher);
            editText23.addTextChangedListener(textWatcher);
            editText24.addTextChangedListener(textWatcher);
            editText25.addTextChangedListener(textWatcher);
            editText26.addTextChangedListener(textWatcher);
            editText27.addTextChangedListener(textWatcher);
            editText28.addTextChangedListener(textWatcher);
            editText29.addTextChangedListener(textWatcher);
            editText30.addTextChangedListener(textWatcher);
            editText31.addTextChangedListener(textWatcher);
            alertDialog = new AlertDialog.Builder(getActivity(), R.style.Dialog).
                    setPositiveButton(R.string.queding, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            turnToDouble();
                            nm = new double[]{nm1, nm2, nm3, nm4, nm5, nm6, nm7, nm8, nm9, nm10, nm11, nm12, nm13, nm14, nm15,
                                    nm16, nm17, nm18, nm19, nm20, nm21, nm22, nm23, nm24, nm25, nm26, nm27, nm28, nm29, nm30, nm31};
                        }
                    }).setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    nm = new double[]{0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 0};
                }
            }).setCancelable(false).create();
            alertDialog.setView(viewDialog);
            alertDialog.show();
            alertDialog.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        } else {
            refButton.setVisibility(View.GONE);
            et1.setVisibility(View.VISIBLE);
            et2.setVisibility(View.VISIBLE);
            et3.setVisibility(View.VISIBLE);
        }
        ArrayList<String> data = (ArrayList) (GlobalBean.datas.get(str));
        if (data.size() > 4) {
            tv4.setVisibility(View.VISIBLE);
            et4.setVisibility(View.VISIBLE);
            tv4.setText(data.get(4));
        } else {
            tv4.setVisibility(View.GONE);
            et4.setVisibility(View.GONE);
        }
        tv1.setText(data.get(1));
        tv2.setText(data.get(2));
        tv3.setText(data.get(3));

    }

    private void turnToDouble() {

        editStr1 = editText1.getText().toString();
        editStr2 = editText2.getText().toString();
        editStr3 = editText3.getText().toString();
        editStr4 = editText4.getText().toString();
        editStr5 = editText5.getText().toString();
        editStr6 = editText6.getText().toString();
        editStr7 = editText7.getText().toString();
        editStr8 = editText8.getText().toString();
        editStr9 = editText9.getText().toString();
        editStr10 = editText10.getText().toString();
        editStr11 = editText11.getText().toString();
        editStr12 = editText12.getText().toString();
        editStr13 = editText13.getText().toString();
        editStr14 = editText14.getText().toString();
        editStr15 = editText15.getText().toString();
        editStr16 = editText16.getText().toString();
        editStr17 = editText17.getText().toString();
        editStr18 = editText18.getText().toString();
        editStr19 = editText19.getText().toString();
        editStr20 = editText20.getText().toString();
        editStr21 = editText21.getText().toString();
        editStr22 = editText22.getText().toString();
        editStr23 = editText23.getText().toString();
        editStr24 = editText24.getText().toString();
        editStr25 = editText25.getText().toString();
        editStr26 = editText26.getText().toString();
        editStr27 = editText27.getText().toString();
        editStr28 = editText28.getText().toString();
        editStr29 = editText29.getText().toString();
        editStr30 = editText30.getText().toString();
        editStr31 = editText31.getText().toString();
        Log.e("main", "editStr12222222222===" + editStr1);
        Log.e("main", "editStr1==null===" + (editStr1 == null));
        Log.e("main", "editStr2==null===" + (editStr2 == null));
        Log.e("main", "editStr2===" + editStr2);
        if (editStr1 == null || editStr1.equals("")) {
            nm1 = 0;
        } else {
            nm1 = Double.parseDouble(editStr1);
        }

        if (editStr2 == null || editStr2.equals("")) {
            nm2 = 0;
        } else {
            nm2 = Double.parseDouble(editStr2);
        }

        if (editStr3 == null || editStr3.equals("")) {
            nm3 = 0;
        } else {
            nm3 = Double.parseDouble(editStr3);
        }
        if (editStr4 == null || editStr4.equals("")) {
            nm4 = 0;
        } else {
            nm4 = Double.parseDouble(editStr4);
        }
        if (editStr5 == null || editStr5.equals("")) {
            nm5 = 0;
        } else {
            nm5 = Double.parseDouble(editStr5);
        }

        if (editStr6 == null || editStr6.equals("")) {
            nm6 = 0;
        } else {
            nm6 = Double.parseDouble(editStr6);
        }

        if (editStr7 == null || editStr7.equals("")) {
            nm7 = 0;
        } else {
            nm7 = Double.parseDouble(editStr7);
        }

        if (editStr8 == null || editStr8.equals("")) {
            nm8 = 0;
        } else {
            nm8 = Double.parseDouble(editStr8);
        }

        if (editStr9 == null || editStr9.equals("")) {
            nm9 = 0;
        } else {
            nm9 = Double.parseDouble(editStr9);
        }

        if (editStr10 == null || editStr10.equals("")) {
            nm10 = 0;
        } else {
            nm10 = Double.parseDouble(editStr10);
        }

        if (editStr11 == null || editStr11.equals("")) {
            nm11 = 0;
        } else {
            nm11 = Double.parseDouble(editStr11);
        }

        if (editStr12 == null || editStr12.equals("")) {
            nm12 = 0;
        } else {
            nm12 = Double.parseDouble(editStr12);
        }

        if (editStr13 == null || editStr13.equals("")) {
            nm13 = 0;
        } else {
            nm13 = Double.parseDouble(editStr13);
        }

        if (editStr14 == null || editStr14.equals("")) {
            nm14 = 0;
        } else {
            nm14 = Double.parseDouble(editStr14);
        }

        if (editStr15 == null || editStr15.equals("")) {
            nm15 = 0;
        } else {
            nm15 = Double.parseDouble(editStr15);
        }

        if (editStr16 == null || editStr16.equals("")) {
            nm16 = 0;
        } else {
            nm16 = Double.parseDouble(editStr16);
        }

        if (editStr17 == null || editStr17.equals("")) {
            nm17 = 0;
        } else {
            nm17 = Double.parseDouble(editStr17);
        }

        if (editStr18 == null || editStr18.equals("")) {
            nm18 = 0;
        } else {
            nm18 = Double.parseDouble(editStr18);
        }

        if (editStr19 == null || editStr19.equals("")) {
            nm19 = 0;
        } else {
            nm19 = Double.parseDouble(editStr19);
        }

        if (editStr20 == null || editStr20.equals("")) {
            nm20 = 0;
        } else {
            nm20 = Double.parseDouble(editStr20);
        }

        if (editStr21 == null || editStr21.equals("")) {
            nm21 = 0;
        } else {
            nm21 = Double.parseDouble(editStr21);
        }

        if (editStr22 == null || editStr22.equals("")) {
            nm22 = 0;
        } else {
            nm22 = Double.parseDouble(editStr22);
        }

        if (editStr23 == null || editStr23.equals("")) {
            nm23 = 0;
        } else {
            nm23 = Double.parseDouble(editStr23);
        }

        if (editStr24 == null || editStr24.equals("")) {
            nm24 = 0;
        } else {
            nm24 = Double.parseDouble(editStr24);
        }

        if (editStr25 == null || editStr25.equals("")) {
            nm25 = 0;
        } else {
            nm25 = Double.parseDouble(editStr25);
        }

        if (editStr26 == null || editStr26.equals("")) {
            nm26 = 0;
        } else {
            nm26 = Double.parseDouble(editStr26);
        }

        if (editStr27 == null || editStr27.equals("")) {
            nm27 = 0;
        } else {
            nm27 = Double.parseDouble(editStr27);
        }

        if (editStr28 == null || editStr28.equals("")) {
            nm28 = 0;
        } else {
            nm28 = Double.parseDouble(editStr28);
        }

        if (editStr29 == null || editStr29.equals("")) {
            nm29 = 0;
        } else {
            nm29 = Double.parseDouble(editStr29);
        }

        if (editStr30 == null || editStr30.equals("")) {
            nm30 = 0;
        } else {
            nm30 = Double.parseDouble(editStr30);
        }
        if (editStr31 == null || editStr31.equals("")) {
            nm31 = 0;
        } else {
            nm31 = Double.parseDouble(editStr31);
        }
    }

    void refreshRightView(String str) {
        Log.e("main", "refreshrightView233====" + str);
        Log.e("main", "str===" + str);
        ArrayList<String> data = (ArrayList) (GlobalBean.datas.get(str));

        if (data.size() > 4) {
            tv8.setVisibility(View.VISIBLE);
            tv4_result.setVisibility(View.VISIBLE);
            tv8.setText(data.get(4));
        } else {
            tv8.setVisibility(View.GONE);
            tv4_result.setVisibility(View.GONE);
        }
        tv5.setText(data.get(1));
        tv6.setText(data.get(2));
        tv7.setText(data.get(3));


    }

    void refreshRight1View(String str) {
        ArrayList<String> data = (ArrayList) (GlobalBean.datas.get(str));
        if (data.size() > 4) {
            tv8.setVisibility(View.VISIBLE);
            tv4_result.setVisibility(View.VISIBLE);
            tv8.setText(data.get(4));
        } else {
            tv8.setVisibility(View.GONE);
            tv4_result.setVisibility(View.GONE);
        }
    }

    //判断字符串是否为数字
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        Pattern pattern1 = Pattern.compile("-?[0-9]");
        Matcher isNum1 = pattern1.matcher(str);

        Pattern pattern2 = Pattern.compile("[0-9]*");
        Matcher isNum2 = pattern2.matcher(str);
        if ((isNum.matches()) || (isNum1.matches()) || (isNum2.matches())) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tip_ref:
                alertDialog.show();
                break;
            case R.id.bt_register:
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.colorpicker_first:
                app.oldcolor = app.color;
                ColorPickerFragment colorpicker = new ColorPickerFragment();
                colorpicker.show(getFragmentManager(), "first");
                break;
            case R.id.button:
                int de = recDeg(leftStr1);
                Log.e("main", "运行了3333");
                Log.e("main", "str1==" + str1);
                if (leftStr.equals("反射率") || leftStr.equals("反射率SCE")) {
                    for (int i = 0; i < 31; i++) {
                        if (nm[i] < 0) {
                            T.showShort(getActivity(), "反射率不能为负数");
                            return;
                        }
                    }
                    List list = color.SCItoRGB(nm, de, degree_mode);
                    double x = (double) list.get(0);
                    double y = (double) list.get(1);
                    double z = (double) list.get(2);
                    refreshButton((int) x, (int) y, (int) z);
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("ISO2470")) {
                    Log.e("main", "SCItoXYZ");
                    iso2470 = color.ref2ISOLight(nm);
                    tv1_result.setText(getDouble(iso2470) + "");
                    tv2_result.setText(getDouble(iso2470) + "");
                    tv3_result.setText(getDouble(iso2470) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("L*c*h")) {
                    Log.e("main", "SCItoLch");
                    List list = color.SCItoCIE_LabCH(nm, de, degree_mode);
                    double x = (double) list.get(0);
                    double y = (double) list.get(3);
                    double z = (double) list.get(4);
                    tv1_result.setText(getDouble(x) + "");
                    tv2_result.setText(getDouble(y) + "");
                    tv3_result.setText(getDouble(z) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("L*a*b*")) {
                    Log.e("main", "SCItoL*a*b*");
                    List list = color.SCItoCIE_LabCH(nm, de, degree_mode);
                    double x = (double) list.get(0);
                    double y = (double) list.get(1);
                    double z = (double) list.get(2);
                    tv1_result.setText(getDouble(x) + "");
                    tv2_result.setText(getDouble(y) + "");
                    tv3_result.setText(getDouble(z) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("Saybolt")) {
                    Log.e("main", "SCItoSaybolt");
                    double saybolt = color.SCItoSaybolt(nm, de, degree_mode);
                    tv1_result.setText(getDouble(saybolt) + "");
                    tv2_result.setText(getDouble(saybolt) + "");
                    tv3_result.setText(getDouble(saybolt) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("ASTM_Color")) {
                    Log.e("main", "SCItoASTM_Color");
                    double ASTM_Color = color.SCItoASTM_Color(nm, de, degree_mode);
                    tv1_result.setText(getDouble(ASTM_Color) + "");
                    tv2_result.setText(getDouble(ASTM_Color) + "");
                    tv3_result.setText(getDouble(ASTM_Color) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("Tint")) {
                    Log.e("main", "SCItoTint");
                    List TintList = color.SCItoWI_TINT_CIE(nm, de, degree_mode);
                    double X1 = (double) TintList.get(0);
                    double Y1 = (double) TintList.get(1);
                    double Z1 = (double) TintList.get(2);
                    if (de != 0x04) {
                        tv1_result.setText("显示条件：D65");
                    } else {
                        tv1_result.setText(getDouble(X1) + "");
                    }
                    if (de != 0x04 || degree_mode != 0) {
                        tv3_result.setText("显示条件：D65/10°");
                    } else {
                        tv3_result.setText(getDouble(Z1) + "");
                    }
                    tv2_result.setText(getDouble(Y1) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("黃度YI")) {
                    Log.e("main", "SCIto黃度YI");
                    List YIList = color.SCItoWI_TINT_CIE(nm, de, degree_mode);
                    double X1 = (double) YIList.get(3);
                    double Y1 = (double) YIList.get(4);
                    double Z1 = (double) YIList.get(5);
                    if (de != 0x01 || degree_mode != 1) {
                        tv1_result.setText("显示条件：C/2°");
                        tv3_result.setText("显示条件：C/2°");
                    } else {
                        tv1_result.setText(getDouble(X1) + "");
                        tv3_result.setText(getDouble(Z1) + "");
                    }
                    tv2_result.setText(getDouble(Y1) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("白度WI")) {
                    Log.e("main", "SCIto白度WI");
                    showPopupWindow(changeBt);
                    List YIList = color.SCItoWI_TINT_CIE(nm, de, degree_mode);
                    double wi1 = (double) YIList.get(6);
                    double wi2 = (double) YIList.get(7);
                    double wi3 = (double) YIList.get(8);
                    double wi4 = (double) YIList.get(9);
                    double wi5 = (double) YIList.get(10);
                    double wi6 = (double) YIList.get(11);
                    double wi7 = (double) YIList.get(12);
                    double wi8 = (double) YIList.get(13);
                    if (de != 0x04 || degree_mode != 0) {
                        vi_text2.setText("显示条件：D65/10°");
                    } else {
                        vi_text2.setText(getDouble(wi2) + "");
                    }
                    if (de != 0x04) {
                        wi_text1.setText("显示条件：D65");
                    } else {
                        wi_text1.setText(getDouble(wi1) + "");
                    }
                    if (de != 0x01 || degree_mode != 1) {
                        vi_text7.setText("显示条件：C/2°");
                    } else {
                        vi_text7.setText(getDouble(wi7) + "");
                    }
                    vi_text3.setText(getDouble(wi3) + "");
                    vi_text4.setText(getDouble(wi4) + "");
                    vi_text5.setText(getDouble(wi5) + "");
                    vi_text6.setText(getDouble(wi6) + "");
                    vi_text7.setText(getDouble(wi7) + "");
                    vi_text8.setText(getDouble(wi8) + "");
                    return;
                }


                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("RGB")) {
                    Log.e("main", "SCItoRGB");
                    List list = color.SCItoRGB(nm, de, degree_mode);
                    double x = (double) list.get(0);
                    double y = (double) list.get(1);
                    double z = (double) list.get(2);
                    tv1_result.setText((int) x + "");
                    tv2_result.setText((int) y + "");
                    tv3_result.setText((int) z + "");
                    refreshButton((int) x, (int) y, (int) z);
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("L*u*v*")) {
                    Log.e("main", "SCItoLuv");
                    List list = color.SCItoLuv(nm, de, degree_mode);
                    double x = (double) list.get(0);
                    double y = (double) list.get(1);
                    double z = (double) list.get(2);
                    tv1_result.setText(getDouble(x) + "");
                    tv2_result.setText(getDouble(y) + "");
                    tv3_result.setText(getDouble(z) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("Hunter Lab")) {
                    Log.e("main", "SCItoHunterLab");
                    List list = color.SCItoHunterLab(nm, de, degree_mode);
                    double x = (double) list.get(0);
                    double y = (double) list.get(1);
                    double z = (double) list.get(2);
                    tv1_result.setText(getDouble(x) + "");
                    tv2_result.setText(getDouble(y) + "");
                    tv3_result.setText(getDouble(z) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("Yxy")) {
                    Log.e("main", "SCItoYxy");
                    List list = color.SCItoYxy(nm, de, degree_mode);
                    double x = (double) list.get(0);
                    double y = (double) list.get(1);
                    double z = (double) list.get(2);
                    tv1_result.setText(getDouble(x) + "");
                    tv2_result.setText(getfourDouble(y) + "");
                    tv3_result.setText(getfourDouble(z) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("MSE_HVC")) {
                    Log.e("main", "SCItoYxy");
                    List list = color.SCItoMSE_HVC(nm, de, degree_mode);
                    double x = (double) list.get(0);
                    double y = (double) list.get(1);
                    double z = (double) list.get(2);
                    tv1_result.setText(getDouble(x) + "R");
                    tv2_result.setText(getDouble(y) + "");
                    tv3_result.setText(getDouble(z) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("XYZ")) {
                    Log.e("main", "SCItoXYZ");
                    List ISO2470List = color.comuteXYZ(nm, de, degree_mode);
                    double x = (double) ISO2470List.get(0);
                    double y = (double) ISO2470List.get(1);
                    double z = (double) ISO2470List.get(2);
                    tv1_result.setText(getDouble(x) + "");
                    tv2_result.setText(getDouble(y) + "");
                    tv3_result.setText(getDouble(z) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("密度A")) {
                    Log.e("main", "SCIto密度A");
                    List ISO2470List = color.Density(nm);
                    double x = (double) ISO2470List.get(2);//Y
                    double y = (double) ISO2470List.get(1);
                    double z = (double) ISO2470List.get(0);
                    tv1_result.setText(getDouble(x) + "");
                    tv2_result.setText(getDouble(y) + "");
                    tv3_result.setText(getDouble(z) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("密度T")) {
                    Log.e("main", "SCIto密度T");
                    List ISO2470List = color.Density(nm);
                    double x = (double) ISO2470List.get(5);//Y
                    double y = (double) ISO2470List.get(4);
                    double z = (double) ISO2470List.get(3);
                    tv1_result.setText(getDouble(x) + "");
                    tv2_result.setText(getDouble(y) + "");
                    tv3_result.setText(getDouble(z) + "");
                    return;
                }

                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("密度E")) {
                    Log.e("main", "SCIto密度E");
                    List ISO2470List = color.Density(nm);
                    double x = (double) ISO2470List.get(11);//Y
                    double y = (double) ISO2470List.get(10);
                    double z = (double) ISO2470List.get(9);
                    tv1_result.setText(getDouble(x) + "");
                    tv2_result.setText(getDouble(y) + "");
                    tv3_result.setText(getDouble(z) + "");
                    return;
                }
                if ((leftStr.equals("反射率") || leftStr.equals("反射率SCE")) && rightStr.equals("密度C")) {
                    Log.e("main", "SCIto密度E");
                    List ISO2470List = color.Density(nm);
                    double x = (double) ISO2470List.get(8);//Y
                    double y = (double) ISO2470List.get(7);
                    double z = (double) ISO2470List.get(6);
                    tv1_result.setText(getDouble(x) + "");
                    tv2_result.setText(getDouble(y) + "");
                    tv3_result.setText(getDouble(z) + "");
                    return;
                }
                if (str1 == null || str2 == null || str3 == null) {
                    Toast.makeText(getActivity(), R.string.fault_tip_null, Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断是否为空
                if (str1.equals("") || str2.equals("") || str3.equals("")) {
                    Toast.makeText(getActivity(), R.string.fault_tip_null, Toast.LENGTH_SHORT).show();
                    Log.e("main", "44444");
                } else {
                    Log.e("main", "str122222====" + str1);
                    Log.e("main", "isNumeric(str1)==" + isNumeric(str1));
                    //解決輸入空格的问题
                    if ((isNumeric(str1)) && (isNumeric(str2)) && (isNumeric(str3))) {
                        Log.e("main", "isNumeric");
                        if (rightStr1.equals("2°")) {
                            degree_mode = 1;
                        } else {
                            degree_mode = 0;
                        }
                        Log.e("main", "str1===" + str1.equals(""));
                        Log.e("main", "length===" + str1.length());
                        double X = Double.parseDouble(str1);
                        double Y = Double.parseDouble(str2);
                        double Z = Double.parseDouble(str3);
                        if (leftStr.equals("XYZ") || leftStr.equals("Yxy")) {
                            if (X < 0 || Y < 0 || Z < 0) {
                                T.showShort(getActivity(), R.string.fault_tip);
                                return;
                            }
                        }
                        if (leftStr.equals("L*a*b*")) {
                            if (X < 0) {
                                T.showShort(getActivity(), "L*参数不能为负数");
                                return;
                            }
                        }
                        if (leftStr.equals("XYZ")) {
                            List list = color.XYZtoCIE_LabCH(X, Y, Z, de, degree_mode);
                            double L = (double) list.get(0);
                            double a = (double) list.get(1);
                            double b = (double) list.get(2);
                            List list2 = color.LabtoRGB(L, a, b);
                            double r = (double) list2.get(0);
                            double g = (double) list2.get(1);
                            double B = (double) list2.get(2);
                            refreshButton((int) r, (int) g, (int) B);
                        }
                        if (leftStr.equals("Yxy")) {
                            List list = color.YxytoRGB(X, Y, Z, de, degree_mode);
                            double L = (double) list.get(0);
                            double a = (double) list.get(1);
                            double b = (double) list.get(2);
                            refreshButton((int) L, (int) a, (int) b);
                        }
                        if (leftStr.equals("L*a*b*")) {
                            List rgbList = color.LabtoRGB(X, Y, Z);
                            double R = (double) rgbList.get(0);
                            double G = (double) rgbList.get(1);
                            double B = (double) rgbList.get(2);
                            refreshButton((int) R, (int) G, (int) B);
                        }

                        if (leftStr.equals("XYZ") && rightStr.equals("L*a*b*")) {
                            Log.e("main", "XYZtoCIE_LabCH");
                            List list = color.XYZtoCIE_LabCH(X, Y, Z, de, degree_mode);
                            double L = (double) list.get(0);
                            double a = (double) list.get(1);
                            double b = (double) list.get(2);
                            /*if(L<=0 ||a<=0||b<=0 ){
                                T.showShort(getActivity(),"X,Y,Z不能为负数");
                            }*/
                            tv1_result.setText(getDouble(L) + "");
                            tv2_result.setText(getDouble(a) + "");
                            tv3_result.setText(getDouble(b) + "");
                        } else if (leftStr.equals("L*a*b*") && rightStr.equals("RGB")) {
                            Log.e("main", "L*a*b*toRGB");
                            List rgbList = color.LabtoRGB(X, Y, Z);
                            double R = (double) rgbList.get(0);
                            double G = (double) rgbList.get(1);
                            double B = (double) rgbList.get(2);
                            tv1_result.setText((int) R + "");
                            tv2_result.setText((int) G + "");
                            tv3_result.setText((int) B + "");
                            refreshButton((int) R, (int) G, (int) B);
                        } else if (leftStr.equals("L*a*b*") && rightStr.equals("XYZ")) {
                            Log.e("main", "L*a*b*toXYZ");
                            Log.e("main", "degree_mode===" + degree_mode);
                            List XYZList = color.CIELabtoXYZ(X, Y, Z, de, degree_mode);
                            double X1 = (double) XYZList.get(0);
                            double Y1 = (double) XYZList.get(1);
                            double Z1 = (double) XYZList.get(2);
                            tv1_result.setText(getDouble(X1) + "");
                            tv2_result.setText(getDouble(Y1) + "");
                            tv3_result.setText(getDouble(Z1) + "");
                        } else if (leftStr.equals("XYZ") && rightStr.equals("L*u*v*")) {
                            Log.e("main", "XYZtoLuv");
                            List LuvList = color.XYZtoLuv(X, Y, Z, de, degree_mode);
                            double L = (double) LuvList.get(0);
                            double u = (double) LuvList.get(1);
                            double v = (double) LuvList.get(2);
                            tv1_result.setText(getDouble(L) + "");
                            tv2_result.setText(getDouble(u) + "");
                            tv3_result.setText(getDouble(v) + "");
                        } else if (leftStr.equals("XYZ") && rightStr.equals("RGB")) {
                            Log.e("main", "XYZtoCIE_LabCH to RGB");
                            List list = color.XYZtoCIE_LabCH(X, Y, Z, de, degree_mode);
                            double L = (double) list.get(0);
                            double a = (double) list.get(1);
                            double b = (double) list.get(2);
                            List list2 = color.LabtoRGB(L, a, b);
                            double r = (double) list2.get(0);
                            double g = (double) list2.get(1);
                            double B = (double) list2.get(2);
                            tv1_result.setText((int) r + "");
                            tv2_result.setText((int) g + "");
                            tv3_result.setText((int) B + "");
                            refreshButton((int) r, (int) g, (int) B);
                        } else if (leftStr.equals("Yxy") && rightStr.equals("RGB")) {
                            Log.e("main", "YxytoCIE_LabCH to RGB");
                            List list = color.YxytoRGB(X, Y, Z, de, degree_mode);
                            double L = (double) list.get(0);
                            double a = (double) list.get(1);
                            double b = (double) list.get(2);
                            tv1_result.setText((int) L + "");
                            tv2_result.setText((int) a + "");
                            tv3_result.setText((int) b + "");
                            refreshButton((int) L, (int) a, (int) b);
                        } else if (leftStr.equals("XYZ") && rightStr.equals("Hunter Lab")) {
                            Log.e("main", "XYZtoHunterLab");
                            List LuvList = color.XYZtoHunterLab(X, Y, Z, de, degree_mode);
                            double HL = (double) LuvList.get(0);
                            double HA = (double) LuvList.get(1);
                            double HB = (double) LuvList.get(2);
                            tv1_result.setText(getDouble(HL) + "");
                            tv2_result.setText(getDouble(HA) + "");
                            tv3_result.setText(getDouble(HB) + "");
                        } else if (leftStr.equals("XYZ") && rightStr.equals("Yxy")) {
                            Log.e("main", "XYZtoYxy");
                            List YxyList = color.XYZtoYxy(X, Y, Z, de, degree_mode);
                            double Y1 = (double) YxyList.get(0);
                            double x = (double) YxyList.get(1);
                            double y = (double) YxyList.get(2);
                            tv1_result.setText(getDouble(Y1) + "");
                            tv2_result.setText(getfourDouble(x) + "");
                            tv3_result.setText(getfourDouble(y) + "");
                        } else if (leftStr.equals("XYZ") && rightStr.equals("L*c*h")) {
                            Log.e("main", "XYZtoLch");
                            List YxyList = color.XYZtoCIE_LabCH(X, Y, Z, de, degree_mode);
                            double Y1 = (double) YxyList.get(0);
                            double x = (double) YxyList.get(3);
                            double y = (double) YxyList.get(4);
                            tv1_result.setText(getDouble(Y1) + "");
                            tv2_result.setText(getDouble(x) + "");
                            tv3_result.setText(getDouble(y) + "");
                        } else if (leftStr.equals("Yxy") && rightStr.equals("L*c*h")) {
                            Log.e("main", "YxytoLch");
                            List YxyList = color.YxytoCIE_LabCH(X, Y, Z, de, degree_mode);
                            double Y1 = (double) YxyList.get(0);
                            double x = (double) YxyList.get(3);
                            double y = (double) YxyList.get(4);
                            tv1_result.setText(getDouble(Y1) + "");
                            tv2_result.setText(getDouble(x) + "");
                            tv3_result.setText(getDouble(y) + "");
                        } else if (leftStr.equals("L*a*b*") && rightStr.equals("L*c*h")) {
                            Log.e("main", "YxytoLch");
                            List YxyList = color.CIELabtoCIE_LabCH(X, Y, Z, de, degree_mode);
                            double Y1 = (double) YxyList.get(0);
                            double x = (double) YxyList.get(3);
                            double y = (double) YxyList.get(4);
                            tv1_result.setText(getDouble(Y1) + "");
                            tv2_result.setText(getDouble(x) + "");
                            tv3_result.setText(getDouble(y) + "");
                        } else if (leftStr.equals("Yxy") && rightStr.equals("XYZ")) {
                            Log.e("main", "YxytoXYZ");
                            List YxyList = color.YxytoXYZ(X, Y, Z);
                            double Y1 = (double) YxyList.get(0);
                            double X1 = (double) YxyList.get(1);
                            double Z1 = (double) YxyList.get(2);
                            tv1_result.setText(getDouble(Y1) + "");
                            tv2_result.setText(getDouble(X1) + "");
                            tv3_result.setText(getDouble(Z1) + "");

                        } else if (leftStr.equals("Yxy") && rightStr.equals("MSE_HVC")) {
                            Log.e("main", "YxytoMSE_HVC");
                            List YxyList = color.YxyToMSE_HVC(X, Y, Z);
                            double Y1 = (double) YxyList.get(0);
                            double X1 = (double) YxyList.get(1);
                            double Z1 = (double) YxyList.get(2);
                            tv1_result.setText(getDouble(Y1) + "R");
                            tv2_result.setText(getDouble(X1) + "");
                            tv3_result.setText(getDouble(Z1) + "");

                        } else if (leftStr.equals("L*a*b*") && rightStr.equals("MSE_HVC")) {
                            Log.e("main", "L*a*b*toMSE_HVC");
                            List YxyList = color.CIELabtoMSE_HVC(X, Y, Z, de, degree_mode);
                            double Y1 = (double) YxyList.get(0);
                            double X1 = (double) YxyList.get(1);
                            double Z1 = (double) YxyList.get(2);
                            tv1_result.setText(getDouble(Y1) + "R");
                            tv2_result.setText(getDouble(X1) + "");
                            tv3_result.setText(getDouble(Z1) + "");
                        } else if (leftStr.equals("XYZ") && rightStr.equals("MSE_HVC")) {
                            Log.e("main", "XYZtoMSE_HVC");
                            List YxyList = color.XYZtoMSE_HVC(X, Y, Z);
                            double Y1 = (double) YxyList.get(0);
                            double X1 = (double) YxyList.get(1);
                            double Z1 = (double) YxyList.get(2);
                            tv1_result.setText(getDouble(Y1) + "R");
                            tv2_result.setText(getDouble(X1) + "");
                            tv3_result.setText(getDouble(Z1) + "");
                        } else if (leftStr.equals(rightStr)) {
                            tv1_result.setText(getDouble(X));
                            tv2_result.setText(getDouble(Y) + "");
                            tv3_result.setText(getDouble(Z) + "");
                        } else if (leftStr.equals("XYZ") && rightStr.equals("ASTM_Color")) {
                            Log.e("main", "XYZtoASTM_Color");
                            if (rightStr1.equals("2°") && de == 0x01) {
                                double STM_Color = color.getASTM_Color(X, Y, Z);
                                tv1_result.setText(getDouble(STM_Color) + "");
                                tv2_result.setText(getDouble(STM_Color) + "");
                                tv3_result.setText(getDouble(STM_Color) + "");
                            } else {
                                tv1_result.setText("显示条件:C/2°");
                                tv2_result.setText("显示条件:C/2°");
                                tv3_result.setText("显示条件:C/2°");
                            }
                        } else if (leftStr.equals("L*a*b*") && rightStr.equals("Saybolt")) {
                            Log.e("main", "L*a*b*toSaybolt");
                            if (rightStr1.equals("2°") && de == 0x01) {
                                double saybolt = color.getSaybolt(X, Y, Z);
                                tv1_result.setText(getDouble(saybolt) + "");
                                tv2_result.setText(getDouble(saybolt) + "");
                                tv3_result.setText(getDouble(saybolt) + "");
                            } else {
                                tv1_result.setText("显示条件:C/2°");
                                tv2_result.setText("显示条件:C/2°");
                                tv3_result.setText("显示条件:C/2°");
                            }
                        } else if (leftStr.equals("XYZ") && rightStr.equals("Saybolt")) {
                            Log.e("main", "XYZtoSaybolt");
                            List list = color.XYZtoCIE_LabCH(X, Y, Z, de, degree_mode);
                            double L = (double) list.get(0);
                            double a = (double) list.get(0);
                            double b = (double) list.get(0);
                            if (rightStr1.equals("2°") && de == 0x01) {
                                double saybolt = color.getSaybolt(L, a, b);
                                tv1_result.setText(getDouble(saybolt) + "");
                                tv2_result.setText(getDouble(saybolt) + "");
                                tv3_result.setText(getDouble(saybolt) + "");
                            } else {
                                tv1_result.setText("显示条件:C/2°");
                                tv2_result.setText("显示条件:C/2°");
                                tv3_result.setText("显示条件:C/2°");
                            }
                        } else if (leftStr.equals("L*a*b*") && rightStr.equals("Hunter Lab")) {
                            Log.e("main", "L*a*b*toHunterLab");
                            List XYZList = color.CIELabtoXYZ(X, Y, Z, de, degree_mode);
                            double X1 = (double) XYZList.get(0);
                            double Y1 = (double) XYZList.get(1);
                            double Z1 = (double) XYZList.get(2);
                            List LuvList = color.XYZtoHunterLab(X1, Y1, Z1, de, degree_mode);
                            double HL = (double) LuvList.get(0);
                            double HA = (double) LuvList.get(1);
                            double HB = (double) LuvList.get(2);
                            tv1_result.setText(getDouble(HL) + "");
                            tv2_result.setText(getDouble(HA) + "");
                            tv3_result.setText(getDouble(HB) + "");
                        } else if (leftStr.equals("L*a*b*") && rightStr.equals("L*u*v*")) {
                            Log.e("main", "L*a*b*toLuv");
                            List XYZList = color.CIELabtoXYZ(X, Y, Z, de, degree_mode);
                            double X1 = (double) XYZList.get(0);
                            double Y1 = (double) XYZList.get(1);
                            double Z1 = (double) XYZList.get(2);
                            List LuvList = color.XYZtoLuv(X1, Y1, Z1, de, degree_mode);
                            double HL = (double) LuvList.get(0);
                            double HA = (double) LuvList.get(1);
                            double HB = (double) LuvList.get(2);
                            tv1_result.setText(X + "");
                            tv2_result.setText(getDouble(HA) + "");
                            tv3_result.setText(getDouble(HB) + "");
                        } else if (leftStr.equals("L*a*b*") && rightStr.equals("Yxy")) {
                            Log.e("main", "L*a*b*toYxy");
                            List XYZList = color.CIELabtoXYZ(X, Y, Z, de, degree_mode);
                            double X1 = (double) XYZList.get(0);
                            double Y1 = (double) XYZList.get(1);
                            double Z1 = (double) XYZList.get(2);
                            List LuvList = color.XYZtoYxy(X1, Y1, Z1, de, degree_mode);
                            double HL = (double) LuvList.get(0);
                            double HA = (double) LuvList.get(1);
                            double HB = (double) LuvList.get(2);
                            tv1_result.setText(getDouble(HL) + "");
                            tv2_result.setText(getfourDouble(HA) + "");
                            tv3_result.setText(getfourDouble(HB) + "");
                        } else if (leftStr.equals("L*a*b*") && rightStr.equals("ASTM_Color")) {
                            Log.e("main", "L*a*b*toASTM_Color");
                            List XYZList = color.CIELabtoXYZ(X, Y, Z, de, degree_mode);
                            double X1 = (double) XYZList.get(0);
                            double Y1 = (double) XYZList.get(1);
                            double Z1 = (double) XYZList.get(2);
                            double ASTM_Color = color.getASTM_Color(X1, Y1, Z1);
                            tv1_result.setText(getDouble(ASTM_Color) + "");
                            tv2_result.setText(getDouble(ASTM_Color) + "");
                            tv3_result.setText(getDouble(ASTM_Color) + "");
                        } else if (leftStr.equals("Yxy") && rightStr.equals("L*u*v*")) {
                            Log.e("main", "YxytoLuv");
                            List XYZList = color.YxytoXYZ(X, Y, Z);
                            double X1 = (double) XYZList.get(0);
                            double Y1 = (double) XYZList.get(1);
                            double Z1 = (double) XYZList.get(2);
                            List LuvList = color.XYZtoLuv(X1, Y1, Z1, de, degree_mode);
                            double HL = (double) LuvList.get(0);
                            double HA = (double) LuvList.get(1);
                            double HB = (double) LuvList.get(2);
                            tv1_result.setText(getDouble(HL) + "");
                            tv2_result.setText(getDouble(HA) + "");
                            tv3_result.setText(getDouble(HB) + "");
                        } else if (leftStr.equals("Yxy") && rightStr.equals("Hunter Lab")) {
                            Log.e("main", "YxytoHunterLab");
                            List XYZList = color.YxytoXYZ(X, Y, Z);
                            double X1 = (double) XYZList.get(0);
                            double Y1 = (double) XYZList.get(1);
                            double Z1 = (double) XYZList.get(2);
                            List LuvList = color.XYZtoHunterLab(X1, Y1, Z1, de, degree_mode);
                            double HL = (double) LuvList.get(0);
                            double HA = (double) LuvList.get(1);
                            double HB = (double) LuvList.get(2);
                            tv1_result.setText(getDouble(HL) + "");
                            tv2_result.setText(getDouble(HA) + "");
                            tv3_result.setText(getDouble(HB) + "");
                        } else if (leftStr.equals("Yxy") && rightStr.equals("L*a*b*")) {
                            Log.e("main", "YxytoL*a*b*");
                            List XYZList = color.YxytoXYZ(X, Y, Z);
                            double X1 = (double) XYZList.get(0);
                            double Y1 = (double) XYZList.get(1);
                            double Z1 = (double) XYZList.get(2);
                            List LuvList = color.XYZtoCIE_LabCH(X1, Y1, Z1, de, degree_mode);
                            double HL = (double) LuvList.get(0);
                            double HA = (double) LuvList.get(1);
                            double HB = (double) LuvList.get(2);
                            tv1_result.setText(getDouble(HL) + "");
                            tv2_result.setText(getDouble(HA) + "");
                            tv3_result.setText(getDouble(HB) + "");
                        } else if (leftStr.equals("Yxy") && rightStr.equals("Saybolt")) {
                            Log.e("main", "YxytoSaybolt");
                            List XYZList = color.YxytoXYZ(X, Y, Z);
                            double X1 = (double) XYZList.get(0);
                            double Y1 = (double) XYZList.get(1);
                            double Z1 = (double) XYZList.get(2);
                            List LuvList = color.XYZtoCIE_LabCH(X1, Y1, Z1, de, degree_mode);
                            double HL = (double) LuvList.get(0);
                            double HA = (double) LuvList.get(1);
                            double HB = (double) LuvList.get(2);
                            if (rightStr1.equals("2°") && de == 0x01) {
                                double saybolt = color.getSaybolt(HL, HA, HB);
                                tv1_result.setText(getDouble(saybolt) + "");
                                tv2_result.setText(getDouble(saybolt) + "");
                                tv3_result.setText(getDouble(saybolt) + "");
                            } else {
                                tv1_result.setText("显示条件:C/2°");
                                tv2_result.setText("显示条件:C/2°");
                                tv3_result.setText("显示条件:C/2°");
                            }
                        } else if (leftStr.equals("Yxy") && rightStr.equals("ASTM_Color")) {
                            Log.e("main", "YxytoASTM_Color");
                            List XYZList = color.YxytoXYZ(X, Y, Z);
                            double X1 = (double) XYZList.get(0);
                            double Y1 = (double) XYZList.get(1);
                            double Z1 = (double) XYZList.get(2);
                            double ASTM_Color = color.getASTM_Color(X1, Y1, Z1);
                            tv1_result.setText(getDouble(ASTM_Color) + "");
                            tv2_result.setText(getDouble(ASTM_Color) + "");
                            tv3_result.setText(getDouble(ASTM_Color) + "");
                        } else if (leftStr.equals("XYZ") && rightStr.equals("Tint")) {
                            Log.e("main", "XYZtoTint");
                            List TintList = color.XYZtoWI_TINT_CIE(X, Y, Z, de, degree_mode);
                            double X1 = (double) TintList.get(0);
                            double Y1 = (double) TintList.get(1);
                            double Z1 = (double) TintList.get(2);
                            if (de != 0x04) {
                                tv1_result.setText("显示条件：D65");
                            } else {
                                tv1_result.setText(getDouble(X1) + "");
                            }
                            if (de != 0x04 || degree_mode != 0) {
                                tv3_result.setText("显示条件：D65/10°");
                            } else {
                                tv3_result.setText(getDouble(Z1) + "");
                            }
                            tv2_result.setText(getDouble(Y1) + "");
                        } else if (leftStr.equals("XYZ") && rightStr.equals("黃度YI")) {
                            Log.e("main", "XYZtoYI");
                            List YIList = color.XYZtoWI_TINT_CIE(X, Y, Z, de, degree_mode);
                            double X1 = (double) YIList.get(3);
                            double Y1 = (double) YIList.get(4);
                            double Z1 = (double) YIList.get(5);
                            if (de != 0x01 || degree_mode != 1) {
                                tv1_result.setText("显示条件：C/2°");
                                tv3_result.setText("显示条件：C/2°");
                            } else {
                                tv1_result.setText(getDouble(X1) + "");
                                tv3_result.setText(getDouble(Z1) + "");
                            }
                            tv2_result.setText(getDouble(Y1) + "");

                        } else if (leftStr.equals("XYZ") && rightStr.equals("白度WI")) {
                            Log.e("main", "XYZtoYI");
                            showPopupWindow(changeBt);
                            List YIList = color.XYZtoWI_TINT_CIE(X, Y, Z, de, degree_mode);
                            double wi1 = (double) YIList.get(6);
                            double wi2 = (double) YIList.get(7);
                            double wi3 = (double) YIList.get(8);
                            double wi4 = (double) YIList.get(9);
                            double wi5 = (double) YIList.get(10);
                            double wi6 = (double) YIList.get(11);
                            double wi7 = (double) YIList.get(12);
                            double wi8 = (double) YIList.get(13);
                            if (de != 0x04 || degree_mode != 0) {
                                vi_text2.setText("显示条件：D65/10°");
                            } else {
                                vi_text2.setText(getDouble(wi2) + "");
                            }
                            if (de != 0x04) {
                                wi_text1.setText("显示条件：D65");
                            } else {
                                wi_text1.setText(getDouble(wi1) + "");
                            }
                            if (de != 0x01 || degree_mode != 1) {
                                vi_text7.setText("显示条件：C/2°");
                            } else {
                                vi_text7.setText(getDouble(wi7) + "");
                            }
                            vi_text3.setText(getDouble(wi3) + "");
                            vi_text4.setText(getDouble(wi4) + "");
                            vi_text5.setText(getDouble(wi5) + "");
                            vi_text6.setText(getDouble(wi6) + "");
                            vi_text7.setText(getDouble(wi7) + "");
                            vi_text8.setText(getDouble(wi8) + "");
                        } else if (leftStr.equals("L*a*b*") && rightStr.equals("Tint")) {
                            Log.e("main", "L*a*b*toTint");
                            List Lablist = new ArrayList();
                            Lablist = color.CIELabtoXYZ(X, Y, Z, de, degree_mode);
                            double X2 = (double) Lablist.get(0);
                            double Y2 = (double) Lablist.get(1);
                            double Z2 = (double) Lablist.get(2);
                            List TintList = color.XYZtoWI_TINT_CIE(X2, Y2, Z2, de, degree_mode);
                            Log.e("main", "X2===" + X2 + ",Y2====" + Y2 + ",Z2===" + Z2);
                            double X1 = (double) TintList.get(0);
                            double Y1 = (double) TintList.get(1);
                            double Z1 = (double) TintList.get(2);
                            if (de != 0x04) {
                                tv1_result.setText("显示条件：D65");
                            } else {
                                tv1_result.setText(getDouble(X1) + "");
                            }
                            if (de != 0x04 || degree_mode != 0) {
                                tv3_result.setText("显示条件：D65/10°");
                            } else {
                                tv3_result.setText(getDouble(Z1) + "");
                            }
                            tv2_result.setText(getDouble(Y1) + "");
                        } else if (leftStr.equals("L*a*b*") && rightStr.equals("黃度YI")) {
                            Log.e("main", "L*a*b*to黃度YI");
                            List Lablist = new ArrayList();
                            Lablist = color.CIELabtoXYZ(X, Y, Z, de, degree_mode);
                            double X2 = (double) Lablist.get(0);
                            double Y2 = (double) Lablist.get(1);
                            double Z2 = (double) Lablist.get(2);
                            List YIList = color.XYZtoWI_TINT_CIE(X2, Y2, Z2, de, degree_mode);
                            double X1 = (double) YIList.get(3);
                            double Y1 = (double) YIList.get(4);
                            double Z1 = (double) YIList.get(5);
                            if (de != 0x01 || degree_mode != 1) {
                                tv1_result.setText("显示条件：C/2°");
                                tv3_result.setText("显示条件：C/2°");
                            } else {
                                tv1_result.setText(getDouble(X1) + "");
                                tv3_result.setText(getDouble(Z1) + "");
                            }
                            tv2_result.setText(getDouble(Y1) + "");
                        } else if (leftStr.equals("L*a*b*") && rightStr.equals("白度WI")) {
                            Log.e("main", "L*a*b*to白度WI");
                            showPopupWindow(changeBt);
                            List Lablist = new ArrayList();
                            Lablist = color.CIELabtoXYZ(X, Y, Z, de, degree_mode);
                            double X2 = (double) Lablist.get(0);
                            double Y2 = (double) Lablist.get(1);
                            double Z2 = (double) Lablist.get(2);
                            List YIList = color.XYZtoWI_TINT_CIE(X2, Y2, Z2, de, degree_mode);
                            double wi1 = (double) YIList.get(6);
                            double wi2 = (double) YIList.get(7);
                            double wi3 = (double) YIList.get(8);
                            double wi4 = (double) YIList.get(9);
                            double wi5 = (double) YIList.get(10);
                            double wi6 = (double) YIList.get(11);
                            double wi7 = (double) YIList.get(12);
                            double wi8 = (double) YIList.get(13);
                            if (de != 0x04 || degree_mode != 0) {
                                vi_text2.setText("显示条件：D65/10°");
                            } else {
                                vi_text2.setText(getDouble(wi2) + "");
                            }
                            if (de != 0x04) {
                                wi_text1.setText("显示条件：D65");
                            } else {
                                wi_text1.setText(getDouble(wi1) + "");
                            }
                            if (de != 0x01 || degree_mode != 1) {
                                vi_text7.setText("显示条件：C/2°");
                            } else {
                                vi_text7.setText(getDouble(wi7) + "");
                            }
                            vi_text3.setText(getDouble(wi3) + "");
                            vi_text4.setText(getDouble(wi4) + "");
                            vi_text5.setText(getDouble(wi5) + "");
                            vi_text6.setText(getDouble(wi6) + "");
                            vi_text7.setText(getDouble(wi7) + "");
                            vi_text8.setText(getDouble(wi8) + "");
                        } else if (leftStr.equals("Yxy") && rightStr.equals("Tint")) {
                            Log.e("main", "YxytoTint");
                            List Lablist = new ArrayList();
                            Lablist = color.YxytoXYZ(X, Y, Z);
                            double X2 = (double) Lablist.get(0);
                            double Y2 = (double) Lablist.get(1);
                            double Z2 = (double) Lablist.get(2);
                            List TintList = color.XYZtoWI_TINT_CIE(X2, Y2, Z2, de, degree_mode);
                            Log.e("main", "X2===" + X2 + ",Y2====" + Y2 + ",Z2===" + Z2);
                            double X1 = (double) TintList.get(0);
                            double Y1 = (double) TintList.get(1);
                            double Z1 = (double) TintList.get(2);
                            if (de != 0x04) {
                                tv1_result.setText("显示条件：D65");
                            } else {
                                tv1_result.setText(getDouble(X1) + "");
                            }
                            if (de != 0x04 || degree_mode != 0) {
                                tv3_result.setText("显示条件：D65/10°");
                            } else {
                                tv3_result.setText(getDouble(Z1) + "");
                            }
                            tv2_result.setText(getDouble(Y1) + "");
                        } else if (leftStr.equals("Yxy") && rightStr.equals("黃度YI")) {
                            Log.e("main", "Yxyto黃度YI");
                            List Lablist = new ArrayList();
                            Lablist = color.YxytoXYZ(X, Y, Z);
                            double X2 = (double) Lablist.get(0);
                            double Y2 = (double) Lablist.get(1);
                            double Z2 = (double) Lablist.get(2);
                            List YIList = color.XYZtoWI_TINT_CIE(X2, Y2, Z2, de, degree_mode);
                            double X1 = (double) YIList.get(3);
                            double Y1 = (double) YIList.get(4);
                            double Z1 = (double) YIList.get(5);
                            if (de != 0x01 || degree_mode != 1) {
                                tv1_result.setText("显示条件：C/2°");
                                tv3_result.setText("显示条件：C/2°");
                            } else {
                                tv1_result.setText(getDouble(X1) + "");
                                tv3_result.setText(getDouble(Z1) + "");
                            }
                            tv2_result.setText(getDouble(Y1) + "");
                        } else if (leftStr.equals("Yxy") && rightStr.equals("白度WI")) {
                            Log.e("main", "Yxyto白度WI");
                            showPopupWindow(changeBt);
                            List Lablist = new ArrayList();
                            Lablist = color.YxytoXYZ(X, Y, Z);
                            double X2 = (double) Lablist.get(0);
                            double Y2 = (double) Lablist.get(1);
                            double Z2 = (double) Lablist.get(2);
                            List YIList = color.XYZtoWI_TINT_CIE(X2, Y2, Z2, de, degree_mode);
                            double wi1 = (double) YIList.get(6);
                            double wi2 = (double) YIList.get(7);
                            double wi3 = (double) YIList.get(8);
                            double wi4 = (double) YIList.get(9);
                            double wi5 = (double) YIList.get(10);
                            double wi6 = (double) YIList.get(11);
                            double wi7 = (double) YIList.get(12);
                            double wi8 = (double) YIList.get(13);
                            if (de != 0x04 || degree_mode != 0) {
                                vi_text2.setText("显示条件：D65/10°");
                            } else {
                                vi_text2.setText(getDouble(wi2) + "");
                            }
                            if (de != 0x04) {
                                wi_text1.setText("显示条件：D65");
                            } else {
                                wi_text1.setText(getDouble(wi1) + "");
                            }
                            if (de != 0x01 || degree_mode != 1) {
                                vi_text7.setText("显示条件：C/2°");
                            } else {
                                vi_text7.setText(getDouble(wi7) + "");
                            }
                            vi_text3.setText(getDouble(wi3) + "");
                            vi_text4.setText(getDouble(wi4) + "");
                            vi_text5.setText(getDouble(wi5) + "");
                            vi_text6.setText(getDouble(wi6) + "");
                            vi_text7.setText(getDouble(wi7) + "");
                            vi_text8.setText(getDouble(wi8) + "");
                        } else {
                            T.showShort(getActivity(), R.string.fault_tip_nomatch);
                        }
                        Log.e("main", "运行了2222");
                    } else {
                        Log.e("main", "else运行");
                        Log.e("main", "(isNumeric(str1)===" + (isNumeric(str1)) + ",,,str1===" + str1);
                        Log.e("main", "(isNumeric(str1))&&(isNumeric(str2))&&(isNumeric(str3))====" + ((isNumeric(str1)) && (isNumeric(str2)) && (isNumeric(str3))));
                        Toast.makeText(getActivity(), "输入不正确", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    //更新颜色Button
    private void refreshButton(int r, int g, int b) {
        String str = TurnColor.toHex(r, g, b);
        bt_colorpicker.setText(str);
        bt_colorpicker.setBackgroundColor(android.graphics.Color.parseColor(str));
    }

    public void showPopupWindow(Button btn) {
        if (popupWindow == null) {
            View view = View.inflate(getActivity(), R.layout.popup_list, null);
            wi_text1 = (TextView) view.findViewById(R.id.wi_text1);
            vi_text2 = (TextView) view.findViewById(R.id.wi_text2);
            vi_text3 = (TextView) view.findViewById(R.id.wi_text3);
            vi_text4 = (TextView) view.findViewById(R.id.wi_text4);
            vi_text5 = (TextView) view.findViewById(R.id.wi_text5);
            vi_text6 = (TextView) view.findViewById(R.id.wi_text6);
            vi_text7 = (TextView) view.findViewById(R.id.wi_text7);
            vi_text8 = (TextView) view.findViewById(R.id.wi_text8);
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //点击popupWindow外部，隐藏popupWindow
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            //第二个参数是X轴偏移，第三个参数Y轴偏移
            popupWindow.showAsDropDown(btn, 250, -650);
            popupWindow.showAtLocation(btn, Gravity.CENTER, 0, 0);
            backgroundAlpha(1.0f);
        }
    }

    /**
     * 背景透明
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    //识别光源
    private int recDeg(String leftStr1) {
        if (leftStr1.equals("A")) {
            m_nGyType = 0x00;
        } else if (leftStr1.equals("C")) {
            m_nGyType = 0x01;
        } else if (leftStr1.equals("D50")) {
            m_nGyType = 0x02;
        } else if (leftStr1.equals("D55")) {
            m_nGyType = 0x03;
        } else if (leftStr1.equals("D65")) {
            m_nGyType = 0x04;
        } else if (leftStr1.equals("D75")) {
            m_nGyType = 0x05;
        } else if (leftStr1.equals("F1")) {
            m_nGyType = 0x06;
        } else if (leftStr1.equals("F2")) {
            m_nGyType = 0x07;
        } else if (leftStr1.equals("F3")) {
            m_nGyType = 0x08;
        } else if (leftStr1.equals("F4")) {
            m_nGyType = 0x09;
        } else if (leftStr1.equals("F5")) {
            m_nGyType = 0x0a;
        } else if (leftStr1.equals("F6")) {
            m_nGyType = 0x0b;
        } else if (leftStr1.equals("F7")) {
            m_nGyType = 0x0c;
        } else if (leftStr1.equals("F8")) {
            m_nGyType = 0x0d;
        } else if (leftStr1.equals("F9")) {
            m_nGyType = 0x0e;
        } else if (leftStr1.equals("F10")) {
            m_nGyType = 0x0f;
        } else if (leftStr1.equals("F11")) {
            m_nGyType = 0x10;
        } else if (leftStr1.equals("F12")) {
            m_nGyType = 0x11;
        } else if (leftStr1.equals("CWF")) {
            m_nGyType = 0x12;
        } else if (leftStr1.equals("U30")) {
            m_nGyType = 0x13;
        } else if (leftStr1.equals("DLF")) {
            m_nGyType = 0x14;
        } else if (leftStr1.equals("NBF")) {
            m_nGyType = 0x15;
        } else if (leftStr1.equals("TL83")) {
            m_nGyType = 0x16;
        } else if (leftStr1.equals("TL84")) {
            m_nGyType = 0x17;
        } else if (leftStr1.equals("U35")) {
            m_nGyType = 0x18;
        }
        return m_nGyType;
    }

    public void setButtonColor(int color, String text) {
        int red = ColorUtils.getRed(color);
        int green = ColorUtils.getGreen(color);
        int blue = ColorUtils.getBlue(color);
        if (Math.sqrt(Math.pow(red, 2) + Math.pow(green, 2) + Math.pow(blue, 2)) > 256) {
            bt_colorpicker.setTextColor(getResources().getColor(android.R.color.black));
        } else {
            bt_colorpicker.setTextColor(getResources().getColor(android.R.color.white));
        }
        bt_colorpicker.setText(text);
        bt_colorpicker.setBackgroundColor(color);
    }
}
