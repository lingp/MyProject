package com.lin.framework.manager;

import android.content.Context;
import android.view.Gravity;

import com.lin.framework.R;
import com.lin.framework.view.DialogView;

/**
 * 提示框管理类
 */
public class DialogManager {

    private static volatile DialogManager mInstance = null;

    private DialogManager() {

    }

    public static DialogManager getInstance() {
        if (mInstance == null) {
            synchronized (DialogManager.class) {
                if (mInstance == null) {
                    mInstance = new DialogManager();
                }
            }
        }
        return mInstance;
    }

    public DialogView initView(Context context, int layout, int gravity) {
        return new DialogView(context, layout, R.style.Theme_Dialog, gravity);
    }

    public DialogView initView(Context context, int layout) {
        return new DialogView(context, layout, R.style.Theme_Dialog, Gravity.CENTER);
    }

    public void show(DialogView view) {
        if (view != null) {
            if (!view.isShowing()) {
                view.show();
            }
        }
    }

    public void hide(DialogView view) {
        if (view != null) {
            if (view.isShowing()) {
                view.dismiss(); // 注意这里是dismiss和hide的区别
            }
        }
    }

}
