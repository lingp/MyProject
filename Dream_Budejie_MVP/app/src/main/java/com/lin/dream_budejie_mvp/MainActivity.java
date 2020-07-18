package com.lin.dream_budejie_mvp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.lin.dream_budejie_mvp.pro.attention.view.AttentionFragment;
import com.lin.dream_budejie_mvp.pro.essence.view.EssenceFragment;
import com.lin.dream_budejie_mvp.pro.mine.view.MineFragment;
import com.lin.dream_budejie_mvp.pro.newpost.view.NewPostFragment;
import com.lin.dream_budejie_mvp.pro.pulish.view.PublishFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TabItem> tabItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 初始化tab数据
    private void initTabData() {
        tabItemList = new ArrayList<>();
        // 添加精华tab
        tabItemList.add(new TabItem(R.drawable.main_bottom_essence_normal, R.drawable.main_bottom_essence_press, R.string.main_essence_text, EssenceFragment.class));
        // 添加新帖
        tabItemList.add(new TabItem(R.drawable.main_bottom_newpost_normal, R.drawable.main_bottom_newpost_press, R.string.main_newpost_text, NewPostFragment.class));
        // 添加发布
        tabItemList.add(new TabItem(R.drawable.main_bottom_public_normal, R.drawable.main_bottom_public_press, 0, PublishFragment.class));
        // 添加关注
        tabItemList.add(new TabItem(R.drawable.main_bottom_attention_normal, R.drawable.main_bottom_attention_press, R.string.main_attention_text, AttentionFragment.class));
        // 添加我的
        tabItemList.add(new TabItem(R.drawable.main_bottom_mine_normal, R.drawable.main_bottom_mine_press, R.string.main_mine_text, MineFragment.class));
    }

    //  初始化主页选项卡视图
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initTabHost() {
        // 获取FragmentTabHost TODO，换掉
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        // 绑定TabHost(绑定body)
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        // 去掉分割线
        fragmentTabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < tabItemList.size(); i++) {
            TabItem tabItem = tabItemList.get(i);
            // 绑定Fragment(将Fragment添加到FragmentTabHost组件上)
            // newTabSpec代表tab名字
            // setIndicator: 图片
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(tabItem.getTitleStr())
                    .setIndicator(tabItem.getView());
            // 添加fragment
            // tabSpec;选项卡
            // tabItem.getFragmentClass 具体的Fragment
            // tabItem.getBundle 给我们的具体的Fragment传参数
            fragmentTabHost.addTab(tabSpec, tabItem.getFragmentClass(), tabItem.getBundle());
            // 设置背景颜色
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getColor(R.color.main_bottom_bg));

            if (i == 0) {
                tabItem.setChecked(true);
            }
        }
    }

    // 代表每一个TAB
    class TabItem {
        // 正常情况下显示的图片
        private int imageNormal;
        // 选中情况下显示的图片
        private int imagePress;
        // tab的名称
        private int title;

        private String titleStr;

        private Class<? extends Fragment> fragmentClass;

        private View view;
        private ImageView imageView;
        private TextView textView;

        private Bundle bundle;

        private Bundle getBundle() {
            if (bundle != null) {
                bundle = new Bundle();
            }

            bundle.putString("title", getTitleStr());

            return bundle;
        }

        public TabItem(int imageNormal, int imagePress, int title, Class<? extends Fragment> fragmentClass) {
            this.imageNormal = imageNormal;
            this.imagePress = imagePress;
            this.title = title;
            this.fragmentClass = fragmentClass;
        }

        public Class<? extends Fragment> getFragmentClass() {
            return fragmentClass;
        }

        public int getImageNormal() {
            return imageNormal;
        }

        public void setImageNormal(int imageNormal) {
            this.imageNormal = imageNormal;
        }

        public int getImagePress() {
            return imagePress;
        }

        public void setImagePress(int imagePress) {
            this.imagePress = imagePress;
        }

        public int getTitle() {
            return title;
        }

        public void setTitle(int title) {
            this.title = title;
        }

        public String getTitleStr() {
            return titleStr;
        }

        public void setTitleStr(String titleStr) {
            this.titleStr = titleStr;
        }

        // 切换tab
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void setChecked(boolean isChecked) {
            if (imageView != null) {
                if (isChecked) {
                    imageView.setImageResource(imagePress);
                } else {
                    imageView.setImageResource(imageNormal);
                }
            }

            if (textView != null && title != 0) {
                if (isChecked) {
                    textView.setTextColor(getColor(R.color.main_bottom_text_select));
                } else {
                    textView.setTextColor(getColor(R.color.main_bottom_text_normal));
                }
            }
        }


        public View getView() {

            if (this.view == null) {
                this.view = getLayoutInflater().inflate(R.layout.view_tab_indicator, null);
                this.imageView = this.view.findViewById(R.id.iv_tab);
                this.textView = this.view.findViewById(R.id.tv_tab);

                if (this.title == 0) {
                    this.textView.setVisibility(View.GONE);
                } else {
                    this.textView.setVisibility(View.VISIBLE);
                }

                this.imageView.setImageResource(imageNormal);
            }
            return this.view;
        }
    }
}
