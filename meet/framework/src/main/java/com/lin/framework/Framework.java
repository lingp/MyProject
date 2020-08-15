package com.lin.framework;

import android.content.Context;

import com.lin.framework.bmob.BombManager;
import com.lin.framework.utils.LogUtils;
import com.lin.framework.utils.SpUtils;

public class Framework {

    private volatile static Framework framework;

    private Framework() {

    }

    public static Framework getFramework() {
        if (framework == null) {
            synchronized (Framework.class) {
                if (framework == null) {
                    framework = new Framework();
                }
            }
        }
        return framework;
    }

    /**
     * 初始化框架 Model
     *
     * @param mContext
     */
    public void initFramework(Context mContext) {
        LogUtils.i("initFramework");
        SpUtils.getInstance().initSp(mContext);
        BombManager.getInstance().initBmob(mContext);

    }


}
