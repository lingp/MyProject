package com.lin.dream_budejie_mvp.pro.base.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lin.dream_budejie_mvp.mvp.presenter.impl.MvpBasePresenter;
import com.lin.dream_budejie_mvp.mvp.view.impl.MvpFragment;

public abstract class BaseFragment<P extends MvpBasePresenter> extends MvpFragment<P> {
    // 缓存视图
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getContentView(), container, false);
            initContentView(view);
            initData();
        }
        // 判断Fragment对应的activity是否存在这个视图
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            // 存在就删除，重新添加
            parent.removeView(view);
        }
        return view;
    }

    @Override
    public P bindPresenter() {
        return null;
    }

    public abstract int getContentView();

    public abstract void initContentView(View view);

    public void initData() {

    }
}
