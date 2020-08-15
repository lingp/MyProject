package com.lin.meet.ui;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.lin.framework.Base.BasePageAdapter;
import com.lin.framework.Base.BaseUIActivity;
import com.lin.framework.manager.MediaPlayerManager;
import com.lin.framework.utils.AnimUtils;
import com.lin.framework.utils.LogUtils;
import com.lin.meet.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseUIActivity implements View.OnClickListener{

    private ViewPager mViewPager;
    private ImageView ivMusicSwitch;
    private TextView tvGuideSkip;
    private ImageView ivGuidePoint1;
    private ImageView ivGuidePoint2;
    private ImageView ivGuidePoint3;

    /**
     * 1.ViewPager: 适配器 | 帧动画播放
     * 2.小圆点的逻辑
     * 3.歌曲的播放
     * 4.属性动画旋转
     * 5.跳转
     */

    private View view1;
    private View view2;
    private View view3;

    private List<View> viewList = new ArrayList<>();
    private BasePageAdapter pageAdapter;

    private ImageView ivGuideStar;
    private ImageView ivGuideNight;
    private ImageView ivGuideSmile;

    private MediaPlayerManager mediaPlayerManager;
    private ObjectAnimator animator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("引导页");
        setContentView(R.layout.guide_layout);
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        ivMusicSwitch = (ImageView) findViewById(R.id.iv_music_switch);
        tvGuideSkip = (TextView) findViewById(R.id.tv_guide_skip);
        ivGuidePoint1 = (ImageView) findViewById(R.id.iv_guide_point_1);
        ivGuidePoint2 = (ImageView) findViewById(R.id.iv_guide_point_2);
        ivGuidePoint3 = (ImageView) findViewById(R.id.iv_guide_point_3);

        ivMusicSwitch.setOnClickListener(this);
        tvGuideSkip.setOnClickListener(this);

        view1 = View.inflate(this, R.layout.layout_pager_guide_1, null);
        view2 = View.inflate(this, R.layout.layout_pager_guide_2, null);
        view3 = View.inflate(this, R.layout.layout_pager_guide_3, null);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        // 预加载
        mViewPager.setOffscreenPageLimit(viewList.size());

        pageAdapter = new BasePageAdapter(viewList);
        mViewPager.setAdapter(pageAdapter);

        // 播放帧动画
        ivGuideStar = view1.findViewById(R.id.iv_guide_star);
        AnimationDrawable animStar = (AnimationDrawable) ivGuideStar.getBackground();
        animStar.start();

        ivGuideNight = view2.findViewById(R.id.iv_guide_night);
        AnimationDrawable animNight = (AnimationDrawable) ivGuideNight.getBackground();
        animNight.start();

        ivGuideSmile = view3.findViewById(R.id.iv_guide_smile);
        AnimationDrawable animSmile = (AnimationDrawable) ivGuideSmile.getBackground();
        animSmile.start();

        // 小圆点逻辑
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 播放音乐
        startMusic();


    }

    /**
     * 动态选择小圆点
     * @param position
     */
    private void selectPoint(int position) {
        switch (position) {
            case 0:
                ivGuidePoint1.setImageResource(R.drawable.img_guide_point_p);
                ivGuidePoint2.setImageResource(R.drawable.img_guide_point);
                ivGuidePoint3.setImageResource(R.drawable.img_guide_point);
                break;
            case 1:
                ivGuidePoint1.setImageResource(R.drawable.img_guide_point);
                ivGuidePoint2.setImageResource(R.drawable.img_guide_point_p);
                ivGuidePoint3.setImageResource(R.drawable.img_guide_point);
                break;
            case 2:
                ivGuidePoint1.setImageResource(R.drawable.img_guide_point);
                ivGuidePoint2.setImageResource(R.drawable.img_guide_point);
                ivGuidePoint3.setImageResource(R.drawable.img_guide_point_p);
                break;
        }
    }

    /**
     * 播放音乐
     */
    private void startMusic() {
        mediaPlayerManager = new MediaPlayerManager();
        final AssetFileDescriptor fileDesc =  getResources().openRawResourceFd(R.raw.guide);
        mediaPlayerManager.setLooping(true);
        mediaPlayerManager.startPlay(fileDesc);

        mediaPlayerManager.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayerManager.startPlay(fileDesc);
            }
        });

        // 帧动画
        animator = AnimUtils.rotation(ivMusicSwitch);
        animator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_music_switch:
                if (mediaPlayerManager.MEDIA_STATUS == MediaPlayerManager.MEDIA_STATUS_PAUSE) {
                    animator.start();
                    mediaPlayerManager.continuPlay();
                    ivMusicSwitch.setImageResource(R.drawable.img_guide_music);
                } else {
                    animator.pause();
                    mediaPlayerManager.pausePlay();
                    ivMusicSwitch.setImageResource(R.drawable.img_guide_music_off);
                }
                break;
            case R.id.tv_guide_skip:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayerManager.stopPlay();
    }
}
