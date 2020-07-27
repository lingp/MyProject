package com.lin.dream_budejie_mvp.pro.mine.view;

import android.view.View;
import android.view.ViewGroup;

import com.lin.dream_budejie_mvp.R;
import com.lin.dream_budejie_mvp.pro.base.view.BaseFragment;
import com.lin.dream_budejie_mvp.pro.mine.view.navigation.MineNavigationBuilder;
import com.lin.dream_budejie_mvp.utils.ToastUtil;

public class MineFragment extends BaseFragment {
    @Override
    public int getContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initContentView(View view) {
        initToolBar(view);
    }

    private void initToolBar(View view) {
        MineNavigationBuilder builder = new MineNavigationBuilder(getContext());
        builder.setModelRes(R.drawable.main_mine_night_model_selector)
                .setModelOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {

                    }
                })
                .setTitle(R.string.main_mine_title_text)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast(getContext(), "点击了");
                    }
                }).setRightIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast(getContext(), "点击了");
                    }
                });

        builder.createAndBind((ViewGroup) view);
    }
}
