package com.lin.dream_budejie_mvp.mvp.presenter.impl;

import com.lin.dream_budejie_mvp.mvp.presenter.MvpPresenter;
import com.lin.dream_budejie_mvp.mvp.view.MvpView;

public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView(V view) {
        if (view != null) {
            view = null;
        }
    }
}
