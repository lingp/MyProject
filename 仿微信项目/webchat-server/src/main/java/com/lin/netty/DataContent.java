package com.lin.netty;

import java.io.Serializable;

public class DataContent implements Serializable {

    private static final long serialVersionUID = -4378404261159027366L;

    private Integer action; // 动作类型
    private ChatMsg chatMsg; // 消息内容
    private String extand; // 扩展字段

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public ChatMsg getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(ChatMsg chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getExtand() {
        return extand;
    }

    public void setExtand(String extand) {
        this.extand = extand;
    }
}
