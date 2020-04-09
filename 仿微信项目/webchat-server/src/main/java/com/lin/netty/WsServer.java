package com.lin.netty;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class WsServer {

    private static class SingletionWsServer {
        static final WsServer instance = new WsServer();
    }

    public static WsServer getInstance() {
        return SingletionWsServer.instance;
    }

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture future;

    public WsServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WsServerInitialzer());
    }
    public void start() {
        this.future = serverBootstrap.bind(8889);
        System.err.println("webSocket 启动完毕");
    }
}
