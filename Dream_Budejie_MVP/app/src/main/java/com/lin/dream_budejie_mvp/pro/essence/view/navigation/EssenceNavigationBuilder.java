package com.lin.dream_budejie_mvp.pro.essence.view.navigation;

import android.content.Context;

import com.lin.dream_budejie_mvp.R;
import com.lin.dream_budejie_mvp.pro.base.view.builder.NavigationBuilderAdapter;

public class EssenceNavigationBuilder extends NavigationBuilderAdapter {

    public EssenceNavigationBuilder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.toolbar_essence_layout;
    }
}
