package com.color.measurement.from_cp.UI.Activitys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.app;
import com.color.measurement.from_cp.Manager.SP.MySharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import com.color.measurement.from_cp.Utils.T;
import com.color.measurement.from_cp.Utils.UiUtils;
import com.color.measurement.from_cp.unused.conn.JsonReqClient;

public class RegisterActivity extends AppCompatActivity {


    private EditText num, yzm;
    private Button bt, singin;

    private String phonenum = "";
    private TimeCount mTimeCount;
    private String mCode = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("注册");
        num = (EditText) findViewById(R.id.et_user_phone_number);
        yzm = (EditText) findViewById(R.id.et_verification_code);

        bt = (Button) findViewById(R.id.btn_get_verification_code);
        singin = (Button) findViewById(R.id.btn_sign_in);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonenum = num.getText().toString().trim();
                if (TextUtils.isEmpty(phonenum)) {
                   T.show(RegisterActivity.this, "手机号码不能为空");
                    return;
                }
                if (!isMobileNO(phonenum)) {
                   T.show(RegisterActivity.this, "手机号码不正确");
                    return;
                }
                sendSMS();
            }
        });
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCode.equals(yzm.getText().toString())) {
                   T.show(RegisterActivity.this, "登陆成功");
                    SharedPreferences preferences = MySharedPreferences.getInstance().getUserInfo();
                    preferences.edit()
                            .putString(MySharedPreferences.USER_PHONE, phonenum)
                            .putBoolean(MySharedPreferences.IS_REGISTER, true)
                            .commit();
                    setResult(RESULT_OK);
                    UiUtils.hiddenSoftInput(RegisterActivity.this);
                    finish();
                } else {
                   T.show(RegisterActivity.this, "验证码不正确");
                }
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、177（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][134578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }

    /**
     * 发送短信验证码
     */
    private void sendSMS() {
        mCode = Math.round(Math.random() * 10000) + "";
        if (mCode.length() > 4) {
            mCode = mCode.substring(0, 4);
        } else if (mCode.length() < 4) {
            sendSMS();
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                JsonReqClient client = new JsonReqClient();
                String result = client.sendVerificationCode(app.APP_ID, app.ACCOUNT_SID,
                        app.AUTH_TOKEN, mCode, phonenum, app.TEMPLATE_ID);
                //{"resp":{"respCode":"000000","templateSMS":{"createDate":"20140820145658","smsId":"d2c49329f363b802fb3531d9c67b54f8"}}}
                if (result != null && result.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object.has("resp")) {
                            JSONObject item = object.getJSONObject("resp");
                            //Code = 105122 同一天同一用户不能发超过10条验证码(因运营商限制一般情况下不足5条)
                            if (item.has("respCode") && item.getString("respCode").equals("000000")) {
                                mHandler.sendEmptyMessage(0);
                            } else {
                                mHandler.sendEmptyMessage(1);
                            }
                        } else {
                            mHandler.sendEmptyMessage(1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mHandler.sendEmptyMessage(1);
                    }
                } else {
                    mHandler.sendEmptyMessage(1);
                }
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 0:
                   T.show(RegisterActivity.this, "验证码发送成功");
                    mTimeCount.start();
                    break;
                case 1:
                   T.show(RegisterActivity.this, "验证码发送失败");
                    break;
            }
        }
    };

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            bt.setText("重新获取");
            bt.setClickable(true);
            bt.setBackgroundResource(R.drawable.btn_purple_style);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            bt.setClickable(false);
            bt.setText("重发(" + millisUntilFinished / 1000 + ")");
            bt.setBackgroundResource(R.drawable.btn_purple_press);
        }
    }


}
