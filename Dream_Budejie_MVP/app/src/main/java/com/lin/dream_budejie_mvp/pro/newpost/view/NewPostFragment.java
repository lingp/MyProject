package com.lin.dream_budejie_mvp.pro.newpost.view;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lin.dream_budejie_mvp.R;
import com.lin.dream_budejie_mvp.pro.base.view.BaseFragment;
import com.lin.dream_budejie_mvp.pro.essence.view.navigation.EssenceNavigationBuilder;
import com.lin.dream_budejie_mvp.pro.newpost.view.adapter.NewpostAdapter;
import com.lin.dream_budejie_mvp.pro.newpost.view.navigation.NewPostNavigationBuilder;
import com.lin.dream_budejie_mvp.utils.ToastUtil;

import java.util.Arrays;

public class NewPostFragment extends BaseFragment {

    private TabLayout tab_newpost;
    private ViewPager vp_newpost;


    @Override
    public int getContentView() {
        return R.layout.fragment_newpost;
    }

    @Override
    public void initContentView(View view) {
        initToolBar(view);
        this.tab_newpost = (TabLayout) view.findViewById(R.id.tab_essence);
        this.vp_newpost = (ViewPager) view.findViewById(R.id.vp_essence);
    }

    private void initToolBar(View view) {
        NewPostNavigationBuilder builder = new NewPostNavigationBuilder(getContext());
        builder.setTitleIcon(R.drawable.main_essence_title)
                .setLeftIcon(R.drawable.main_newpost_audit_btn_selector)
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
        String[] titles = getResources().getStringArray(R.array.newpost_video_tab);
        NewpostAdapter adapter =
                new NewpostAdapter(getFragmentManager(), Arrays.asList(titles));
        this.vp_newpost.setAdapter(adapter);
        this.tab_newpost.setupWithViewPager(this.vp_newpost);
    }
}
