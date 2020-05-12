package com.wen.notice.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.util.HashMap;
import java.util.Map;

/**
 *  监听器
 */
public class UserNoticeListener implements ChannelAwareMessageListener {

    private static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        // 获取用户id  通过队列名称
        String queueName = message.getMessageProperties().getConsumerQueue();
        String userId = queueName.substring(queueName.lastIndexOf("_") + 1);

        io.netty.channel.Channel webChannel = MyWebsocketHandler.userChannelMap.get(userId);

        if (webChannel != null) {
            // 表示用户在线
            // 封装返回数据
            Map<String, Integer> map = new HashMap<>();
            // 每来一条消息 + 1
            map.put("userNoticeCount", 1);

            Result result = Result.ok(ResultCode.SUCCESS, map);
            // 把数据发送给用户
            webChannel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(result)));


        }

    }
}
