package com.wen.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "ten-user")
@Component
public interface UserClient {

    @PutMapping("/user/{userId}/{friendId}/{num}")
    void updateFanscountFollowcount(@PathVariable("userId") String userId,
                                           @PathVariable("friendId") String friendId,
                                           @PathVariable("num") Integer num);

}
