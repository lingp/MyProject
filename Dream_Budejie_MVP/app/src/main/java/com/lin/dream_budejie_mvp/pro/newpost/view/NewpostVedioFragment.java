package com.lin.dream_budejie_mvp.pro.newpost.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lin.dream_budejie_mvp.R;
import com.lin.dream_budejie_mvp.pro.base.view.BaseFragment;

/**
 * Created by Dream on 16/5/27.
 */
public class NewpostVedioFragment extends BaseFragment {

    private int mType = 0;
    private String mTitle;

    public void setType(int mType) {
        this.mType = mType;
    }
    public void setTitle(String title){
        this.mTitle = title;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_essence_video;
    }

    @Override
    public void initContentView(View contentView) {
        TextView textView = (TextView)contentView.findViewById(R.id.tv_content);
        textView.setText(this.mTitle);
    }

    @Override
    public void initData() {
    }



}
