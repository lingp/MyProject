package com.lin.dream_budejie_mvp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        // 启动页
        // 四种方案
        // 第一种：属性动画ObjectAnimator实现
        // 第二种：补间动画
        // 第三种：定时器
        // 第四种：Handler + Thread实现


        // 属性动画
        // 1、第一个参数 target: 要对哪个view绑定动画
        View target = findViewById(R.id.img_bg);
        // 2、第二个参数 propertyName: 要执行什么动画, 例：scalx缩放动画
        // 3、第三个参数 ...values: 动画的变化范围（例：缩放动画变化范围0.0 ~ 1.0之间）
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "alpha", 0.1f, 1.0f);
        // 设置动画执行的时间
        objectAnimator.setDuration(2000);
        // 启动动画
        objectAnimator.start();

        // 结束动画，跳转主页面
//        objectAnimator.addListener(new Animator.AnimatorListener() {
////
////            @Override
////            public void onAnimationStart(Animator animation) {
////
////            }
////
////            @Override
////            public void onAnimationEnd(Animator animation) {
////                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
////                finish();
////            }
////
////            @Override
////            public void onAnimationCancel(Animator animation) {
////            }
////
////            @Override
////            public void onAnimationRepeat(Animator animation) {
////
////            }
////        });
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
