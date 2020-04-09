package com.lin.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 用于监测channel的心跳
 */
public class HeartChatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        // 判断是否是IdleStateEvent 用于触发用户时间，包含读空闲/写空闲/读写空闲
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("读空闲");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("写空闲");
            } else if (event.state() == IdleState.ALL_IDLE) { // 读和写都空闲
                System.out.println("channel关闭钱：数量" + ChatHandler.clientsChannels.size());
                Channel channel = ctx.channel();
                channel.close();
                System.out.println("channel关闭后：数量" + ChatHandler.clientsChannels.size());
            }
        }
    }
}
