package com.lin.dream_budejie_mvp.mvp.presenter;

import com.lin.dream_budejie_mvp.mvp.view.MvpView;

public interface MvpPresenter<V extends MvpView> {

    // 绑定视图
    public void attachView(V view);

    // 解除绑定
    public  void detachView(V view);
}
