package com.lin.dream_budejie_mvp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lin.dream_budejie_mvp.pro.attention.view.AttentionFragment;
import com.lin.dream_budejie_mvp.pro.base.view.BaseFragment;
import com.lin.dream_budejie_mvp.pro.essence.view.EssenceFragment;
import com.lin.dream_budejie_mvp.pro.mine.view.MineFragment;
import com.lin.dream_budejie_mvp.pro.newpost.view.NewPostFragment;
import com.lin.dream_budejie_mvp.pro.pulish.view.PublishFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<Fragment>(
            Arrays.asList(
                    new EssenceFragment(),
                    new NewPostFragment(),
                    new PublishFragment(),
                    new AttentionFragment(),
                    new MineFragment()
                    )
    );

    public MyFragmentPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
//        return "测试";
    }
}


