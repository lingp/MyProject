package com.lin.dream_budejie_mvp.mvp.view.impl;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lin.dream_budejie_mvp.mvp.presenter.impl.MvpBasePresenter;
import com.lin.dream_budejie_mvp.mvp.view.MvpView;

// 集成MVP架构到我们的Activity
public abstract class MvpActivity<P extends MvpBasePresenter> extends AppCompatActivity implements MvpView {
    // P是动态的
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = bindPresenter();

        // 绑定view
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    // 具体实现不知道，给子类实现
    public abstract P bindPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解除绑定
        presenter.detachView(this);
    }
}
