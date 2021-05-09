package com.lin.meet.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.meet.R;
import com.lin.meet.model.StarModel;
import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.List;

/**
 * 3D星球适配器
 */
public class CloudTagAdapter extends TagsAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    public CloudTagAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        // 初始化View
        View view = inflater.inflate(R.layout.layout_star_view_item, null);
        // 初始化控件
        ImageView ivStarIcon = view.findViewById(R.id.iv_star_icon);
        TextView tvStarName = view.findViewById(R.id.tv_star_name);

        tvStarName.setText(list.get(position));

        switch (position % 10) {
            case 0:
                ivStarIcon.setImageResource(R.drawable.img_star_icon_1);
                break;
            case 1:
                ivStarIcon.setImageResource(R.drawable.img_star_icon_2);
                break;
            case 2:
                ivStarIcon.setImageResource(R.drawable.img_star_icon_3);
                break;
            case 3:
                ivStarIcon.setImageResource(R.drawable.img_star_icon_4);
                break;
            case 4:
                ivStarIcon.setImageResource(R.drawable.img_star_icon_5);
                break;
            case 5:
                ivStarIcon.setImageResource(R.drawable.img_star_icon_6);
                break;
            default:
                ivStarIcon.setImageResource(R.drawable.img_star_icon_7);
                break;
        }
        return view;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }

    class ViewHolder {
        private ImageView ivStarIcon;
        private TextView tvStarName;
    }
}
