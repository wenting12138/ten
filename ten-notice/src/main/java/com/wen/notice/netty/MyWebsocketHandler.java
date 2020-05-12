package com.wen.notice.netty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  业务处理类
 */
public class MyWebsocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ObjectMapper objectMapper = new ObjectMapper();

    // 存放websocket连接
    public static ConcurrentHashMap<String, Channel> userChannelMap = new ConcurrentHashMap<String, Channel>();
    // 从spring容器中获取rabbitTemplate
    private RabbitTemplate rabbitTemplate = ApplicationContextProvider.getApplicationContext().getBean(RabbitTemplate.class);

    private static final String queueNamePrefix = "article_subscribe_";

    private static final String article_thumbup = "article_thumbup_";

    // 从spring容器获取消息监听器  处理订阅消息
    private SimpleMessageListenerContainer sysNoticeContainer = (SimpleMessageListenerContainer) ApplicationContextProvider
            .getApplicationContext().getBean("sysNoticeContainer");
    // 从spring容器获取消息监听器  处理点赞消息
    private SimpleMessageListenerContainer userNoticeContainer = (SimpleMessageListenerContainer) ApplicationContextProvider
            .getApplicationContext().getBean("userNoticeContainer");
    /**
     *  用户请求websocket服务器, 执行方法
     */
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 约定用户的第一个请求携带的数据: {"userId": "1"}
        String json  = msg.text();
        // 解析json数据
        JsonNode jsonNode = objectMapper.readTree(json);
        String userId = jsonNode.get("userId").asText();

        Channel channel = userChannelMap.get(userId);
        if (channel == null) {
            // 获取连接
            channel = ctx.channel();
            userChannelMap.put(userId, channel);
        }

        // 获取rabbitmq的消息内容
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        // 获取消息数量
        String queueName = queueNamePrefix + userId;
        Properties queueProperties = rabbitAdmin.getQueueProperties(queueName);
        // 消息数量
        int noticeCount = 0;
        if (queueProperties != null) {
            noticeCount = (int) queueProperties.get("QUEUE_MESSAGE_COUNT");
        }

        // 获取点赞类消息
        String userQueueName = article_thumbup + userId;
        Properties userQueueProperties = rabbitAdmin.getQueueProperties(userQueueName);
        // 消息数量
        int userNoticeCount = 0;
        if (userQueueProperties != null) {
            userNoticeCount = (int) queueProperties.get("QUEUE_MESSAGE_COUNT");
        }


        // 封装返回的数据
        Map<String, Integer> map = new HashMap();
        // 订阅类消息数量
        map.put("sysNoticeCount", noticeCount);
        // 点赞类消息数量
        map.put("userNoticeCount", userNoticeCount);

        Result result = Result.ok(ResultCode.SUCCESS, map);
        // 返回数据
        channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(result)));

        // 清空消息, 否则mq消息被监听器监听又要消费一次
        if (noticeCount > 0) {
            rabbitAdmin.purgeQueue(queueName, true);
        }
        if (userNoticeCount > 0) {
            rabbitAdmin.purgeQueue(userQueueName, true);
        }

        // 为用户的消息通知队列注册监听器, 用于用户在线时,主动推送给用户
        sysNoticeContainer.addQueueNames(queueName);
        userNoticeContainer.addQueueNames(userQueueName);
    }
}
