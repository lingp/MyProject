package com.lin.dream_budejie_mvp.mvp.view.impl;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lin.dream_budejie_mvp.mvp.presenter.impl.MvpBasePresenter;
import com.lin.dream_budejie_mvp.mvp.view.MvpView;

public abstract class MvpFragment<P extends MvpBasePresenter> extends Fragment implements MvpView {

    protected P presenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 绑定视图
        presenter = bindPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    // 绑定
    public abstract P bindPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView(this);
        }
    }
}
