package com.lin.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class WsServerInitialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // websocket基于http协议，需要http编码器
        pipeline.addLast(new HttpServerCodec());
        // 大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对HttpMessage进行聚合
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        // websocket服务器处理协议，用于指定给客户端连接访问的路由:/ws
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 自定义的handler
        pipeline.addLast(new ChatHandler());

        // 心跳支持
        pipeline.addLast(new IdleStateHandler(20, 40, 60));
        pipeline.addLast(new HeartChatHandler());

    }
}
