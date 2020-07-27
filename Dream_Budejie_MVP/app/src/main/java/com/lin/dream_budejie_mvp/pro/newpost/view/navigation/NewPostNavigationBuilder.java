package com.lin.dream_budejie_mvp.pro.newpost.view.navigation;

import android.content.Context;

import com.lin.dream_budejie_mvp.R;
import com.lin.dream_budejie_mvp.pro.base.view.builder.NavigationBuilderAdapter;

public class NewPostNavigationBuilder extends NavigationBuilderAdapter {

    public NewPostNavigationBuilder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.toolbar_newpost_layout;
    }
}
