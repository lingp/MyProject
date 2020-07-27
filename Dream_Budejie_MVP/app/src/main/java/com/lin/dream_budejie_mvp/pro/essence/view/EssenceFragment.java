package com.lin.dream_budejie_mvp.pro.essence.view;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lin.dream_budejie_mvp.R;
import com.lin.dream_budejie_mvp.pro.base.view.BaseFragment;
import com.lin.dream_budejie_mvp.pro.essence.view.adapter.EssenceAdapter;
import com.lin.dream_budejie_mvp.pro.essence.view.navigation.EssenceNavigationBuilder;
import com.lin.dream_budejie_mvp.utils.ToastUtil;

import java.util.Arrays;

public class EssenceFragment extends BaseFragment {

    private TabLayout tab_essence;
    private ViewPager vp_essence;

    @Override
    public int getContentView() {
        return R.layout.fragment_assence;
    }

    @Override
    public void initContentView(View view) {
        initToolBar(view);
        this.tab_essence = (TabLayout) view.findViewById(R.id.tab_essence);
        this.vp_essence = (ViewPager) view.findViewById(R.id.vp_essence);
    }

    private void initToolBar(View view) {
        EssenceNavigationBuilder builder = new EssenceNavigationBuilder(getContext());
        builder.setTitleIcon(R.drawable.main_essence_title)
                .setLeftIcon(R.drawable.main_essence_btn_selector)
                .setRightIcon(R.drawable.main_essence_btn_more_selector)
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

    @Override
    public void initData() {
        String[] titles = getResources().getStringArray(R.array.essence_video_tab);
        Log.e("测试", titles[0]);

        EssenceAdapter adapter =
                new EssenceAdapter(getFragmentManager(), Arrays.asList(titles));
        this.vp_essence.setAdapter(adapter);
        this.tab_essence.setupWithViewPager(this.vp_essence);
    }
}
