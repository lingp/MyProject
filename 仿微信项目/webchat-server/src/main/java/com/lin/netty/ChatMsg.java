package com.lin.netty;

import java.io.Serializable;

public class ChatMsg implements Serializable {
    private static final long serialVersionUID = -8850076799878240014L;

    private String sendId; // 发送者Id
    private String receiveId; // 接受者Id
    private String msg; // 聊天内容
    private String msgId; //消息id,用于消息签收

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
