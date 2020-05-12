package com.wen.notice.netty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  netty 配置类
 */
@Configuration
public class NettyConfig {

    @Bean
    public NettyServer nettyServer(){
        NettyServer nettyServer = new NettyServer();
        // 启动netty服务
        new Thread(() -> {
            nettyServer.start(1234);
        }).start();
        return nettyServer;
    }

}
