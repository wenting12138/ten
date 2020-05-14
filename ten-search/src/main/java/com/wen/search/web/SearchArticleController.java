package com.wen.search.web;

import com.wen.common.result.PageResult;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import com.wen.common.result.ResultService;
import com.wen.search.model.Article;
import com.wen.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/article")
@RestController
public class SearchArticleController {

    @Autowired
    private SearchService searchService;

    @PostMapping
    public Result save(@RequestBody Article article){
        ResultService<Void> service = searchService.save(article);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    @GetMapping("/{key}/{page}/{size}")
    public Result searchPage(@PathVariable("key") String key,
                             @PathVariable("page") int page,
                             @PathVariable("size") int size){
        ResultService<PageResult<Article>> service = searchService.searchPage(key, page, size);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }
}
