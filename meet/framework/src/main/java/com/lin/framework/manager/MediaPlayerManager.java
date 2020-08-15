package com.lin.framework.manager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.lin.framework.utils.LogUtils;

import java.io.IOException;

/**
 * 媒体播放
 */
public class MediaPlayerManager {
    // 播放
    public static final int MEDIA_STATUS_PLAY = 0;
    // 暂停
    public static final int MEDIA_STATUS_PAUSE = 1;
    // 停止
    public static final int MEDIA_STATUS_STOP = 2;

    // 当前状态
    public  int MEDIA_STATUS = MEDIA_STATUS_PAUSE;

    private static MediaPlayer mediaPlayer;

    onMusicProgressListner mMusicProgressListner;

    private static final int H_PROGRESS = 1000;

    public MediaPlayerManager() {
        mediaPlayer = new MediaPlayer();
    }

    /**
     * 播放
     * @param path
     */
    public void startPlay(AssetFileDescriptor path) {

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path.getFileDescriptor(), path.getStartOffset(), path.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            handler.sendEmptyMessage(H_PROGRESS);
        } catch (IOException e) {
            LogUtils.e(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 是否在播放状态
     * @return
     */
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    /**
     * 暂停播放
     */
    public void pausePlay() {
        if (isPlaying()) {
            mediaPlayer.pause();
            MEDIA_STATUS = MEDIA_STATUS_PAUSE;
            handler.removeMessages(H_PROGRESS);
        }
    }

    /**
     * 继续播放
     */
    public void continuPlay() {
        mediaPlayer.start();
        MEDIA_STATUS = MEDIA_STATUS_PLAY;
        handler.sendEmptyMessage(H_PROGRESS);
    }


    /**
     * 停止播放
     */
    public void stopPlay() {
        mediaPlayer.stop();
        MEDIA_STATUS = MEDIA_STATUS_STOP;
        handler.removeMessages(H_PROGRESS);
    }

    /**
     * 获取当前位置
     * @return
     */
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    /**
     * 获取总时长
     * @return
     */
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    /**
     * 监听播放完成
     * @param listener
     */
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        mediaPlayer.setOnCompletionListener(listener);
    }

    /**
     * 是否循环
     * @param isLooping
     */
    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }

    /**
     * 设置进度
     * @param ms
     */
    public void seekTo(int ms) {
        mediaPlayer.seekTo(ms);
    }

    /**
     * 监听播放出错
     * @param listener
     */
    public void setOnErrorListener(MediaPlayer.OnErrorListener listener) {
        mediaPlayer.setOnErrorListener(listener);
    }

    /**
     * 播放进度监听
     */
    public void setOnProgressListner(onMusicProgressListner musicProgressListner) {
        mMusicProgressListner = musicProgressListner;
    }

    public interface  onMusicProgressListner {
        void onProgress(int progress, int pos);
    }

    /**
     * 计算歌曲的播放进度
     * 1.开始播放的时候就开启循环计算时长
     * 2. 将进度计算结果对外抛出
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case H_PROGRESS:
                    if (mMusicProgressListner != null) {
                        int currentPosition = getCurrentPosition();
                        int pos = (int)(((float)currentPosition/(float) getDuration()) * 100);
                        mMusicProgressListner.onProgress(currentPosition, pos);
                        handler.sendEmptyMessageAtTime(H_PROGRESS, 1000);
                    }
                    break;
                default:;
            }

            return false;
        }
    });
}
