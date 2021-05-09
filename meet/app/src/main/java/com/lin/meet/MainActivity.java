package com.lin.meet;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lin.framework.Base.BaseUIActivity;
import com.lin.framework.manager.MediaPlayerManager;
import com.lin.framework.utils.LogUtils;
import com.lin.framework.utils.TimeUtils;
import com.lin.meet.fragment.ChatFragment;
import com.lin.meet.fragment.MeFragment;
import com.lin.meet.fragment.SquareFragment;
import com.lin.meet.fragment.StarFragment;

import java.util.List;

public class MainActivity extends BaseUIActivity implements View.OnClickListener {

    private static  final  int PREMISSON_REQUEST_CODE = 1000;

    // 星球
    private ImageView ivStar;
    private TextView tvStar;
    private StarFragment starFragment;
    private FragmentTransaction starFragmentTransaction;
    private LinearLayout llStar;

    // 广场
    private ImageView ivSquare;
    private TextView tvSquare;
    private SquareFragment squareFragment;
    private FragmentTransaction squareFragmentTransaction;
    private LinearLayout llSquare;


    // 聊天
    private ImageView ivChat;
    private TextView tvChat;
    private ChatFragment chatFragment;
    private FragmentTransaction chatFragmentTransaction;
    private LinearLayout llChat;


    // 我的
    private ImageView ivMe;
    private TextView tvMe;
    private MeFragment meFragment;
    private FragmentTransaction meFragmentTransaction;
    private LinearLayout llMe;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        requirePermiss();

        ivStar = findViewById(R.id.ivStar);
        tvStar = findViewById(R.id.tvStar);
        llStar = findViewById(R.id.llStar);

        ivSquare = findViewById(R.id.ivSqure);
        tvSquare = findViewById(R.id.tvSqure);
        llSquare = findViewById(R.id.llSqure);

        ivChat = findViewById(R.id.ivChat);
        tvChat = findViewById(R.id.tvChat);
        llChat = findViewById(R.id.llChat);

        ivMe = findViewById(R.id.ivMe);
        tvMe = findViewById(R.id.tvMe);
        llMe = findViewById(R.id.llMe);

        llStar.setOnClickListener(this);
        llSquare.setOnClickListener(this);
        llChat.setOnClickListener(this);
        llMe.setOnClickListener(this);

        // 设置文本
        tvStar.setText(getString(R.string.text_main_star));
        tvSquare.setText(getString(R.string.text_main_square));
        tvChat.setText(getString(R.string.text_main_chat));
        tvMe.setText(getString(R.string.text_main_me));


        // 初始化fragment
        initFragment();

        // 切换默认的选项卡
        changeMainTab(0);

    }

    /**
     * 切换默认的选项卡
     * @param index
     */
    private void changeMainTab(int index) {
        switch (index) {
            case 0:
                showFragment(starFragment);

                ivStar.setImageResource(R.drawable.img_star_p);
                ivSquare.setImageResource(R.drawable.img_square);
                ivChat.setImageResource(R.drawable.img_chat);
                ivMe.setImageResource(R.drawable.img_me);

                tvStar.setTextColor(getResources().getColor(R.color.colorAccent));
                tvSquare.setTextColor(Color.BLACK);
                tvChat.setTextColor(Color.BLACK);
                tvMe.setTextColor(Color.BLACK);

                break;
            case 1:
                showFragment(squareFragment);

                ivStar.setImageResource(R.drawable.img_star);
                ivSquare.setImageResource(R.drawable.img_square_p);
                ivChat.setImageResource(R.drawable.img_chat);
                ivMe.setImageResource(R.drawable.img_me);

                tvStar.setTextColor(Color.BLACK);
                tvSquare.setTextColor(getResources().getColor(R.color.colorAccent));
                tvChat.setTextColor(Color.BLACK);
                tvMe.setTextColor(Color.BLACK);

                break;
            case 2:
                showFragment(chatFragment);

                ivStar.setImageResource(R.drawable.img_star);
                ivSquare.setImageResource(R.drawable.img_square);
                ivChat.setImageResource(R.drawable.img_chat_p);
                ivMe.setImageResource(R.drawable.img_me);

                tvStar.setTextColor(Color.BLACK);
                tvSquare.setTextColor(Color.BLACK);
                tvChat.setTextColor(getResources().getColor(R.color.colorAccent));
                tvMe.setTextColor(Color.BLACK);

                break;
            case 3:
                showFragment(meFragment);

                ivStar.setImageResource(R.drawable.img_star);
                ivSquare.setImageResource(R.drawable.img_square);
                ivChat.setImageResource(R.drawable.img_chat);
                ivMe.setImageResource(R.drawable.img_me_p);

                tvStar.setTextColor(Color.BLACK);
                tvSquare.setTextColor(Color.BLACK);
                tvChat.setTextColor(Color.BLACK);
                tvMe.setTextColor(getResources().getColor(R.color.colorAccent));

                break;
        }
    }

    /**
     * 显示Fragment
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        if (fragment != null) {
            LogUtils.i("测试");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            hideAllFragment(transaction);
            transaction.show(fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 隐藏所有的fragment
     * @param transaction
     */
    private void hideAllFragment(FragmentTransaction transaction) {
        if (starFragment != null) {
            transaction.hide(starFragment);
        }
        if (squareFragment != null) {
            transaction.hide(squareFragment);
        }
        if (chatFragment != null) {
            transaction.hide(chatFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
    }

    // 初始化fragment
    private void initFragment() {
        // 星球
        if (starFragment == null) {
            starFragment = new StarFragment();
            starFragmentTransaction = getSupportFragmentManager().beginTransaction();
            starFragmentTransaction.add(R.id.mainLayout, starFragment);
            starFragmentTransaction.commit(); // 记得提交
        }

        // 广场
        if (squareFragment == null) {
            squareFragment = new SquareFragment();
            squareFragmentTransaction = getSupportFragmentManager().beginTransaction();
            squareFragmentTransaction.add(R.id.mainLayout, squareFragment);
            squareFragmentTransaction.commit();
        }

        // 聊天
        if (chatFragment == null) {
            chatFragment = new ChatFragment();
            chatFragmentTransaction = getSupportFragmentManager().beginTransaction();
            chatFragmentTransaction.add(R.id.mainLayout, chatFragment);
            chatFragmentTransaction.commit();
        }

        // 我的
        if (meFragment == null) {
            meFragment = new MeFragment();
            meFragmentTransaction = getSupportFragmentManager().beginTransaction();
            meFragmentTransaction.add(R.id.mainLayout, meFragment);
            meFragmentTransaction.commit();
        }
    }

    /**
     * 请求权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requirePermiss() {

        if (checkWindowPermissons()) {
            requestWindowFeature(1001);
        }

        request(PREMISSON_REQUEST_CODE, new OnPermissonResult() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(List<String> noPermissons) {
                LogUtils.i("noPermissons:" + noPermissons.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llStar:
                changeMainTab(0);
                break;
            case R.id.llSqure:
                changeMainTab(1);
                break;
            case R.id.llChat:
                changeMainTab(2);
                break;
            case R.id.llMe:
                changeMainTab(3);
                break;
        }
    }

    /**
     * 防止重叠
     * 当应用内存紧张时，系统回收掉Fragment对象
     * 再一次进入的时候重新创建Fragment
     * 非原来对象，无法控制
     * @param fragment
     */
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (starFragment != null && fragment instanceof StarFragment) {
            starFragment = (StarFragment) fragment;
        }
        if (squareFragment != null && fragment instanceof SquareFragment) {
            squareFragment = (SquareFragment) fragment;
        }
        if (chatFragment != null && fragment instanceof ChatFragment) {
            chatFragment = (ChatFragment) fragment;
        }
        if (meFragment != null && fragment instanceof MeFragment) {
            meFragment = (MeFragment) fragment;
        }
    }
}
