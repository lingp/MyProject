package com.lin.dream_budejie_mvp.pro.attention.view.navigation;

import android.content.Context;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lin.dream_budejie_mvp.R;
import com.lin.dream_budejie_mvp.pro.base.view.builder.NavigationBuilderAdapter;


/**
 * Created by Dream on 16/5/27.
 */
public class AttentionNavigationBuilder extends NavigationBuilderAdapter {

    private ViewPager viewPager;

    public AttentionNavigationBuilder(Context context){
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.toolbar_attention_layout;
    }

    public AttentionNavigationBuilder setUpWithViewPager(ViewPager viewPager){
        this.viewPager = viewPager;
        return this;
    }

    @Override
    public void createAndBind(ViewGroup parent) {
        super.createAndBind(parent);
        setImageViewStyle(R.id.iv_left,getLeftIconRes(),getLeftIconOnClickListener());
        TabLayout tab_attention = (TabLayout) findViewById(R.id.tab_attention);
        tab_attention.setupWithViewPager(viewPager);
    }
}
