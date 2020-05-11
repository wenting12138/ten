package com.wen.encrypt.service;

import com.wen.encrypt.EncryptMain9013;
import com.wen.encrypt.rsa.RsaKeys;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest(classes = EncryptMain9013.class)
@RunWith(SpringRunner.class)
public class RsaServiceTest {

    @Autowired
    private RsaService rsaService;

    @Test
    public void RSADecryptDataPEM() {
    }

    @Test
    public void RSADecryptDataBytes() {
    }

    @Test
    public void RSAEncryptDataPEM() throws Exception {

        String data = "{\"title\": \"java\"}";
        // 用公钥进行加密
        String encode = rsaService.RSAEncryptDataPEM(data, RsaKeys.getServerPubKey());
        System.out.println(encode);

    }

    @Test
    public void getRsaAlgorithm() throws Exception {
        // 解密
        String requestData = "tuDU5hEuayKbY0jdZvEGKErmHZc8hm4vCvt6OGxlUYGJdZm0ZRVCi34Bftj3gL/JFqzEyxpaXwjfOTjTwaGDvLEZVKfDhoyTXPf0pekhljZo7R2o9hmX/7v2qJAnkeunBJqPzv+sgn8KbikzfOKUTa4MU7SV/KCcFD+CSt0RyXVmrV5MLoghsIQQi4MOJetVzDgDUnSR+3U92txxFyGXIjt3IzL10WED83p2NAnh26TG+J+VSnbHdMCcvj92rM980wDc4mvnG6udSJ0NUK4AAKW18VsJOHPbggubIw/rSuxlB4AfZmLE+ppnj0eiPThZMM+t2yWFrV/OBsIFjwB7KQ==";

        System.out.println(rsaService.RSADecryptDataPEM(requestData, RsaKeys.getServerPrvKeyPkcs8()));

    }
}