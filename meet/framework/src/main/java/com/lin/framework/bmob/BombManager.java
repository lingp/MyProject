package com.lin.framework.bmob;

import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Bomb管理类
 */
public class BombManager {
    private static final String BMOB_SDK_ID = "2ce9c150880d17010fbb579b86258dd1";

    private volatile static BombManager bombManager = null;

    private BombManager() {

    }

    public static BombManager getInstance() {
        if (bombManager == null) {
            synchronized (BombManager.class) {
                if (bombManager == null) {
                    bombManager = new BombManager();
                }
            }
        }
        return bombManager;
    }

    /**
     * 初始化Bmob
     * @param context
     */
    public void initBmob(Context context) {
        Bmob.initialize(context, BMOB_SDK_ID);
    }

    /**
     * 是否登录
     * @return
     */
    public boolean isLogin() {
        return BmobUser.isLogin();
    }

    /**
     * 获取本地对象
     * @return
     */
    public IMUser getUser() {
        return BmobUser.getCurrentUser(IMUser.class);
    }

    // 发送短信验证码
    public void requestSMS(String phone, QueryListener<Integer> listener) {
        BmobSMS.requestSMSCode(phone, "", listener);
    }

    /**
     * 通过手机号码注册或者登陆
     *
     * @param phone    手机号码
     * @param code     短信验证码
     * @param listener 回调
     */
    public void signOrLoginByMobilePhone(String phone, String code, LogInListener<IMUser> listener) {
        BmobUser.signOrLoginByMobilePhone(phone, code, listener);
    }
}
