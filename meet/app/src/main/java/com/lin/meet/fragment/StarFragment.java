package com.lin.meet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lin.framework.Base.BaseFragment;
import com.lin.meet.R;
import com.lin.meet.adapter.CloudTagAdapter;
import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;


public class StarFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvStarTitle;
    private ImageView iv_camera;
    private ImageView iv_add;

    private TagCloudView mCloudView;

    private LinearLayout ll_random;
    private LinearLayout ll_soul;
    private LinearLayout ll_fate;
    private LinearLayout ll_love;

    private CloudTagAdapter mCloudTagAdapter;
    private List<String> mStarList = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star, null);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mCloudView = view.findViewById(R.id.mCloudView);

        for (int i = 0; i < 100; i++) {
            mStarList.add("Star" + i);
        }
        mCloudTagAdapter = new CloudTagAdapter(getActivity(), mStarList);
        // 数据绑定
        mCloudView.setAdapter(mCloudTagAdapter);

        // 监听点击时间
        mCloudView.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
