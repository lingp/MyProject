package com.lin.dream_budejie_mvp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import android.os.Bundle;

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
        tabItemList.add(new TabItem(R.drawable.main_bottom_essence_normal, R.drawable.main_bottom_essence_press, R.string.main_essence_text));
        // 添加新帖
        tabItemList.add(new TabItem(R.drawable.main_bottom_newpost_normal, R.drawable.main_bottom_newpost_press, R.string.main_newpost_text));
        // 添加发布
        tabItemList.add(new TabItem(R.drawable.main_bottom_public_normal, R.drawable.main_bottom_public_press, 0));
        // 添加关注
        tabItemList.add(new TabItem(R.drawable.main_bottom_attention_normal, R.drawable.main_bottom_attention_press, R.string.main_attention_text));
        // 添加我的
        tabItemList.add(new TabItem(R.drawable.main_bottom_mine_normal, R.drawable.main_bottom_mine_press, R.string.main_mine_text));
    }

    //  初始化主页选项卡视图
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

        public TabItem(int imageNormal, int imagePress, int title) {
            this.imageNormal = imageNormal;
            this.imagePress = imagePress;
            this.title = title;
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
    }
}
