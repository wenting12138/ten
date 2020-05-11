package com.wen.article.feignclient;

import com.wen.common.model.Notice;
import com.wen.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ten-notice")
public interface NoticeClient {

    /**
     *  新增通知
     */
    @PostMapping("/notice")
    public Result save(@RequestBody Notice notice);

}
