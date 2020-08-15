package com.lin.meet;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.lin.framework.Base.BaseUIActivity;
import com.lin.framework.manager.MediaPlayerManager;
import com.lin.framework.utils.LogUtils;
import com.lin.framework.utils.TimeUtils;

public class MainActivity extends BaseUIActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // 5.0以下不支持MediaPlayer
//        final MediaPlayerManager mediaPlayerManager = new MediaPlayerManager();
//        AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(R.raw.guide);
//        mediaPlayerManager.startPlay(fileDescriptor);
//
//        mediaPlayerManager.setOnProgressListner(new MediaPlayerManager.onMusicProgressListner() {
//            @Override
//            public void onProgress(int progress, int pos) {
//                LogUtils.i("p:" + pos);
//            }
//        });
    }
}
