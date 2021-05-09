package com.lin.framework.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.lin.framework.R;
import com.lin.framework.manager.DialogManager;
import com.lin.framework.utils.AnimUtils;

/**
 * 加载提示框
 */
public class LoadingView {

    private DialogView dialogView;
    private ImageView ivLoading;
    private TextView tvLoadingText;
    private ObjectAnimator animator;

    public LoadingView(Context context) {
        dialogView = DialogManager.getInstance().initView(context, R.layout.dialog_loading);
        ivLoading = dialogView.findViewById(R.id.iv_loading);
        tvLoadingText = dialogView.findViewById(R.id.iv_loading_text);
        animator = AnimUtils.rotation(ivLoading);
    }

    /**
     * 设置加载的提示文本
     * @param text
     */
    public void setLoadingText(String text) {
        if (!TextUtils.isEmpty(text)) {
            tvLoadingText.setText(text);
        }
    }

    public void show() {
        animator.start();
        DialogManager.getInstance().show(dialogView);
    }

    public void show(String text) {
        setLoadingText(text);
        DialogManager.getInstance().show(dialogView);
    }

    public void hide() {
        animator.pause();
        DialogManager.getInstance().hide(dialogView);
    }

}
