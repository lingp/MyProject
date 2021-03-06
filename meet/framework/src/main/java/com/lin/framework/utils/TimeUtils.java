package com.lin.framework.utils;

public class TimeUtils {
    /**
     * 转换毫秒格式
     * @param ms
     */
    public static String formatDuring(long ms) {
        long hours = (ms % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (ms % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (ms % (1000 * 60)) / 1000;

        String h = hours + "";
        String m = minutes + "";
        String s = seconds + "";
        if (hours < 10) {
            h = "0" + h;
        }
        if (minutes < 10) {
            m = "0" + m;
        }
        if (seconds < 10) {
            s = "0" + s;
        }

        return h + ":" + m + ":" + s;
    }
}
