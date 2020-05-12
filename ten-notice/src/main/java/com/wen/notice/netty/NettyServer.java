package com.wen.notice.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

    public void start(int port){

        ServerBootstrap bootstrap = new ServerBootstrap();
        log.info("准备启动netty~~~");
        // 处理新连接
        NioEventLoopGroup boos = new NioEventLoopGroup();
        // 处理业务逻辑的
        NioEventLoopGroup work = new NioEventLoopGroup();

        bootstrap.group(boos, work)
                // 端口号
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        // 请求消息解码器
                        channel.pipeline().addLast(new HttpServerCodec());
                        // 将多个消息转换为单一的request 或 response
                        channel.pipeline().addLast(new HttpObjectAggregator(65536));
                        // 处理websocket的消息事件
                        channel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));

                        // 自己的websocket处理器
                        MyWebsocketHandler myWebsocketHandler = new MyWebsocketHandler();
                        channel.pipeline().addLast(myWebsocketHandler);
                    }
                }).bind(port);
    }

}
