package com.wen.sms.lisener;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.sms.config.SmsConfig;
import com.wen.sms.utils.SmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class SmsListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SmsConfig config;

    @Autowired
    private SmsUtils smsUtils;

    @RabbitListener(queues = "sms")
    public void sms(String msg) throws IOException {
        Map<String, String> map = objectMapper.readValue(msg, Map.class);
        // 解析到手机号和验证码
        String mobile = map.get("mobile");
        String code = map.get("code");
        log.info("mobile: {}, code: {}", mobile, code);

        // 发短信:
        try {
            smsUtils.sendSms(mobile, code, config.getSignName(), config.getTemplateCode());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
