package com.lin.dream_budejie_mvp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;
import androidx.viewpager.widget.ViewPager;


import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.lin.dream_budejie_mvp.pro.attention.view.AttentionFragment;
import com.lin.dream_budejie_mvp.pro.essence.view.EssenceFragment;
import com.lin.dream_budejie_mvp.pro.mine.view.MineFragment;
import com.lin.dream_budejie_mvp.pro.newpost.view.NewPostFragment;
import com.lin.dream_budejie_mvp.pro.pulish.view.PublishFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private List<TabItem> tabItemList;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyFragmentPageAdapter myFragmentPageAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        viewPager = findViewById(R.id.pager);
        myFragmentPageAdapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPageAdapter);

        tabLayout = findViewById(R.id.tab_Layout);
        tabLayout.setupWithViewPager(viewPager);

        tabItemList = new ArrayList<>();
        // 添加精华tab
        tabItemList.add(new TabItem(R.drawable.main_bottom_essence_normal, R.drawable.main_bottom_essence_press, R.string.main_essence_text, EssenceFragment.class, tabLayout.getTabAt(0)));
        // 添加新帖
        tabItemList.add(new TabItem(R.drawable.main_bottom_newpost_normal, R.drawable.main_bottom_newpost_press, R.string.main_newpost_text, NewPostFragment.class, tabLayout.getTabAt(1)));
        // 添加发布
        tabItemList.add(new TabItem(R.drawable.main_bottom_public_normal, R.drawable.main_bottom_public_press, 0, PublishFragment.class, tabLayout.getTabAt(2)));
        // 添加关注
        tabItemList.add(new TabItem(R.drawable.main_bottom_attention_normal, R.drawable.main_bottom_attention_press, R.string.main_attention_text, AttentionFragment.class, tabLayout.getTabAt(3)));
        // 添加我的
        tabItemList.add(new TabItem(R.drawable.main_bottom_mine_normal, R.drawable.main_bottom_mine_press, R.string.main_mine_text, MineFragment.class, tabLayout.getTabAt(4)));
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

        private TabLayout.Tab tab;

        private Bundle bundle;

        private Bundle getBundle() {
            if (bundle != null) {
                bundle = new Bundle();
            }

            bundle.putString("title", getTitleStr());

            return bundle;
        }

        public TabItem(int imageNormal, final int imagePress, int title, Class<? extends Fragment> fragmentClass, TabLayout.Tab tab) {
            this.imageNormal = imageNormal;
            this.imagePress = imagePress;
            this.title = title;
            this.fragmentClass = fragmentClass;
            this.tab = tab;

            this.tab.setText(getTitleStr());
            this.tab.setIcon(getImageNormal());

            Class cls = this.tab.getClass();
            try {
                Field field = cls.getDeclaredField("view");
                field.setAccessible(true);

                View view = (View) field.get(this.tab);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      for(int i = 0; i < tabItemList.size(); i++) {
                            if (tabItemList.get(i).getTab() == getTab()) {
                                getTab().setIcon(getImagePress());
                            } else {
                                tabItemList.get(i).getTab().setIcon(tabItemList.get(i).getImageNormal());
                            }
                      }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

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
            if (title == 0) {
                return "";
            }

            if (TextUtils.isEmpty(this.titleStr)) {
                titleStr = getString(this.title);
            }
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

        public TabLayout.Tab getTab() {
            return tab;
        }

        public void setTab(TabLayout.Tab tab) {
            this.tab = tab;
        }
    }
}
