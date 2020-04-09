package com.lin.netty;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * 用户ID和channel的关联关系处理
 */
public class UserChannelRef {
    private static HashMap<String, Channel> map = new HashMap<>();

    public static void put(String sendId, Channel channel) {
        map.put(sendId, channel);
    }

    public static Channel get(String sendId) {
        return map.get(sendId);
    }

    public static void output() {
        for (HashMap.Entry<String, Channel> entry: map.entrySet()) {
            System.out.println("UserId:" + entry.getKey()
                    + ",ChannelId:" + entry.getValue().id().asLongText());
        }
    }
}
