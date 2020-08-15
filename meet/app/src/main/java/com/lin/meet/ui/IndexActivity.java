package com.lin.meet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.lin.framework.Base.BaseUIActivity;
import com.lin.framework.entity.Constants;
import com.lin.framework.utils.SpUtils;
import com.lin.meet.MainActivity;
import com.lin.meet.R;

/**
 * 启动页
 * 1、把启动页全屏
 * 2. 延迟进入主页
 * 3. 根据具体逻辑进入主页
 * 4. 适配刘海屏
 *
 */
public class IndexActivity extends BaseUIActivity {

    private static final int SKIP_MAIN = 1000;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SKIP_MAIN:
                    startMain();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        handler.sendEmptyMessageDelayed(SKIP_MAIN, 2000);
    }

    /**
     * 进入主页
     */
    private void startMain() {
        // 1.判断是否第一次启动 (安装后的第一次启动)
        // 放到BaseApp初始化
//        SpUtils.getInstance().initSp(getBaseContext());
        Boolean isFristAppp =SpUtils.getInstance().getBoolean(Constants.SP_IS_FIRST_APP, true);
        Intent intent = new Intent();
        if (isFristAppp) {// 第一次启动，跳转到引导页
            intent.setClass(this, GuideActivity.class);
            SpUtils.getInstance().putBoolean(Constants.SP_IS_FIRST_APP, false);
        } else {
            // 非第一次启动，判断是否登录
            String token = SpUtils.getInstance().getString(Constants.SP_TOKEN, "");
            if (TextUtils.isEmpty(token)) { // token为空，跳转到登录页
                intent.setClass(this, LoginActivity.class);
            } else { // token存在，跳转到主页
                intent.setClass(this, MainActivity.class);
            }
        }
        startActivity(intent);
        finish();
    }
}
