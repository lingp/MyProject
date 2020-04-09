package com.lin.netty;

import com.lin.SpringUtil;
import com.lin.enums.MsgActionEnum;
import com.lin.service.UserService;
import com.lin.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理消息的handler， TextWebSocketFrame用于websocket专门处理文本的对象
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    // 用于记录和管理客户端的channel
    public static ChannelGroup clientsChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String content = msg.text();

        Channel currentChannel = ctx.channel();
        // 1.获取客户端传输的消息
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        Integer action = dataContent.getAction();

        // 2.判断消息类型,根据不同类型来处理不同的业务
        if (action == MsgActionEnum.CONNECT.type) {
            // 2.1 当websocket第一次open的时候，初始化channel，把channel和userId关联起来
            String sendId = dataContent.getChatMsg().getSendId();
            UserChannelRef.put(sendId, currentChannel);

            // 调试
            for (Channel c: clientsChannels) {
                System.out.println(c.id().asLongText());
            }
            UserChannelRef.output();

        } else if (action == MsgActionEnum.CHAT.type) {
            // 2.2 聊天类型的消息，把聊天记录保存到数据库（正常要加密），同时标记消息的签收状态[未签收]
            System.out.println(dataContent);
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msgVal = chatMsg.getMsg();
            String receiveId = chatMsg.getReceiveId();
            String sendId = chatMsg.getSendId();

            UserService userService = (UserService) SpringUtil.getBean("userServiceImpl");
            String msgId =  userService.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);

            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setChatMsg(chatMsg);

            // 发送消息
            Channel receiveChannel = UserChannelRef.get(receiveId);
            if (receiveChannel == null) {
                // TODO 离线消息推送
            } else {
                System.out.println("接收到的数据：" + content);
                Channel findChannel = clientsChannels.find(receiveChannel.id());
                if (findChannel != null) { // 用户在线
                    receiveChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContentMsg)));
                } else {
                    // TODO 离线消息推送
                }
            }

        } else if (action == MsgActionEnum.SIGNED.type) {
            // 2.3 签收消息[已签收],终端收到消息的意思
            UserService userService = (UserService) SpringUtil.getBean("userServiceImpl");
            String msgIdsStr = dataContent.getExtand(); // 代表需要去签收的消息id,逗号间隔
            System.out.println("hello world");
            System.out.println(dataContent);
            System.out.println(dataContent.getExtand());
            String msgIds[] = msgIdsStr.split(",");
            List<String> msgIdList = new ArrayList<>();
            for(String mid : msgIds) {
                if(StringUtils.isNoneBlank(mid)) {
                    msgIdList.add(mid);
                }
            }
            System.out.println(msgIdList);

            if (msgIdList != null && !msgIdList.isEmpty() && msgIdList.size() > 0) {
                userService.updateMsgSigned(msgIdList);
            }

        } else if (action == MsgActionEnum.KEEPALIVE.type) {
            // 2.4 心跳类型
            System.out.println("收到来自channel为[" + currentChannel + "]的心跳包...");
        }







    }

    // 客户端和服务端连接，获取客户端并放到ChannelGroup管理
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clientsChannels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发handlerRemoved, ChannelGroup可以自动移除
        clientsChannels.remove(ctx.channel());
        System.out.println("客户端断开， channel对应的长ID为：" + ctx.channel().id().asLongText());
        System.out.println("客户端断开， channel对应的短ID为：" + ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常，关闭连接，并移除
        ctx.channel().close();
        clientsChannels.remove(ctx.channel());
    }

}
