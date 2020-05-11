package com.wen.notice.feignclient;

import com.wen.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ten-article")
public interface ArticleClient {

    /**
     *  根据文章id查询文章数据
     */
    @GetMapping("/article/{article}")
    Result selectOne(@PathVariable("article") String articleId);
}
