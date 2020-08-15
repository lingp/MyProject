package com.lin.meet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lin.framework.Base.BaseUIActivity;
import com.lin.framework.bmob.BombManager;
import com.lin.framework.bmob.IMUser;
import com.lin.framework.entity.Constants;
import com.lin.framework.utils.LogUtils;
import com.lin.framework.utils.SpUtils;
import com.lin.meet.MainActivity;
import com.lin.meet.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * 登录页
 * 1.点击发送的按钮，弹出一个提示框，图片验证码，验证通过之后
 * 2.!发送验证码，@同时按钮变成不可点击，@按钮开始倒计时，倒计时结束，@按钮可点击，@文字变成“发送”
 * 3.通过手机号码和验证码进行登录
 * 4.登录成功之后获取本地对象
 */
public class LoginActivity extends BaseUIActivity implements View.OnClickListener{
    private EditText etPhone;
    private EditText etCode;
    private Button btnSendCode;
    private Button btnLogin;
    private TextView tvTestLogin;
    private TextView tvUserAgreement;

    // 60秒倒计时
    private static final int H_TIME = 1001;
    private static int TIME = 60;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case H_TIME:
                    btnSendCode.setText(TIME + "s");
                    if (TIME > 0) {
                        handler.sendEmptyMessageDelayed(H_TIME, 1000);
                        TIME--;
                    } else {
                        btnSendCode.setEnabled(true);
                        btnSendCode.setText(getString(R.string.text_login_send));
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
//        startActivity(new Intent(this, TestActivity.class));
        initView();
    }

    private void initView() {

        initDialogView();

        etPhone = (EditText) findViewById(R.id.et_phone);
        etCode = (EditText) findViewById(R.id.et_code);
        btnSendCode = (Button) findViewById(R.id.btn_send_code);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvTestLogin = (TextView) findViewById(R.id.tv_test_login);
        tvUserAgreement = (TextView) findViewById(R.id.tv_user_agreement);

        btnSendCode.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        String phone = SpUtils.getInstance().getString(Constants.SP_PHONE, "");
        if (!TextUtils.isEmpty(phone)) {
            etPhone.setText(phone);
        }
    }

    private void initDialogView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_code:
                sendSms();
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login() {
        //1.判断手机号码和验证码不为空
        final String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getString(R.string.text_login_phone_null),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, getString(R.string.text_login_code_null),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        BombManager.getInstance().signOrLoginByMobilePhone(phone, code, new LogInListener<IMUser>() {
            @Override
            public void done(IMUser imUser, BmobException e) {
                if (e == null) {
                    //登陆成功
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //把手机号码保存下来
                    SpUtils.getInstance().putString(Constants.SP_PHONE, phone);
                    finish();
                } else {
                    if (e.getErrorCode() == 207) {
                        Toast.makeText(LoginActivity.this, getString(R.string.text_login_code_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "ERROR:" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 发送短信验证码
     */
    private void sendSms() {
        // 1.获取手机号码
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getString(R.string.text_login_phone_null), Toast.LENGTH_SHORT).show();
            return;
        }

        //2.请求短信验证码
        BombManager.getInstance().requestSMS(phone, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    btnSendCode.setEnabled(false);
                    handler.sendEmptyMessage(H_TIME);
                    Toast.makeText(LoginActivity.this, getString(R.string.text_user_resuest_succeed), Toast.LENGTH_SHORT).show();
                } else {
                    LogUtils.e("SMS:" + e.toString());
                    Toast.makeText(LoginActivity.this,  getString(R.string.text_user_resuest_fail),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    


}
