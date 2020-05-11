package com.wen.notice.feignclient;

import com.wen.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ten-user")
public interface UserClient {

    /**
     *  根据id查看用户信息
     */
    @GetMapping("/user/{userId}")
    Result getOneUser(@PathVariable("userId") String userId);
}
