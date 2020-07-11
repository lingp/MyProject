package com.lin.dream_budejie_mvp.mvp.view.impl;

import com.lin.dream_budejie_mvp.mvp.view.MvpView;

/**
 * 请求数据，刷新UI界面监听（标准）-- 其实就是loading页
 */
public interface MvpLceView<M> extends MvpView {
    // 显示加载中的视图（下拉刷新/上拉加载）
    public void showLoading(boolean pullToRefresh);

    // 显示ContentView
    public void showContent();

    // 显示错误信息
    public void showError(Exception e, boolean pullToRefresh);

    // 绑定数据
    public void bindData(M data);
}
