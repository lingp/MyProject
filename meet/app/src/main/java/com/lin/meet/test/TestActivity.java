package com.lin.meet.test;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lin.framework.Base.BaseActivity;
import com.lin.framework.view.TouchPictureV;
import com.lin.meet.R;

public class TestActivity extends BaseActivity {

    private TouchPictureV touchPictureV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        touchPictureV = (TouchPictureV) findViewById(R.id.touchPictureV);
        touchPictureV.setViewResultListener(new TouchPictureV.OnViewResultListener() {
            @Override
            public void onResult() {
                Toast.makeText(TestActivity.this, "验证通过", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
