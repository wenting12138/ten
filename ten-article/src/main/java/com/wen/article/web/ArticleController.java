package com.wen.article.web;

import com.sun.org.apache.regexp.internal.RE;
import com.wen.article.dto.ArticleDto;
import com.wen.article.service.ArticleService;
import com.wen.common.model.Article;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import com.wen.common.result.ResultService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     *  根据id查询文章信息
     * @param articleId
     * @return
     */
    @GetMapping("/{article}")
    public Result selectOne(@PathVariable("article") String articleId){
        ResultService<Article> resultService = articleService.selectOneById(articleId);
        if (resultService.isB()) {
            return Result.ok(ResultCode.SUCCESS, resultService.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  查询所有文章列表
     * @return
     */
    @GetMapping
    public Result selectAll(){
        ResultService<List<Article>> resultService = articleService.selectAll();
        if (resultService.isB()) {
            return Result.ok(ResultCode.SUCCESS, resultService.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  保存文章
     * @param article
     * @return
     */
    @PostMapping
    public Result save(@RequestBody ArticleDto article){
        if (article == null) {
            return Result.ok(ResultCode.FAIL, null);
        }
        ResultService<Void> service = articleService.save(article);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  修改文章
     * @param article
     * @return
     */
    @PutMapping("/{articleId}")
    public Result update(@RequestBody ArticleDto article,@PathVariable("articleId") String articleId){
        if (article == null) {
            return Result.ok(ResultCode.FAIL, null);
        }
        ResultService<Void> service = articleService.update(article, articleId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  条件查询  并且分页
     */
    @PostMapping("/search/{page}/{size}")
    public Result selectByCondition(@RequestBody(required = false) ArticleDto article,
                                    @PathVariable(value = "page", required = false) Integer page,
                                    @PathVariable(value = "size", required = false) Integer size){
        ResultService<List<Article>> service = null;
        if (article == null) {
            article = new ArticleDto();
        }
        if (page == null || size == null) {
            service = articleService.selectByCondition(article);
        }
        service = articleService.selectByConditionPageSize(article, page, size);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  删除文章
     */
    @DeleteMapping("/{articleId}")
    public Result delete(@PathVariable("articleId") String articleId){
        ResultService<Void> service = articleService.delete(articleId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }


    /**
     *  点赞
     */
    @PutMapping("/thumbup/{articleId}")
    public Result addThumbup(@PathVariable("articleId") String articleId){
        ResultService<Void> service = articleService.addThumbup(articleId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  根据频道获取文章
     */
    @PostMapping("/channel/{channelId}/{page}/{size}")
    public Result getArticleByChannelPageSize(@PathVariable("channelId") String channeIdl,
                                              @PathVariable(value = "page", required = false) int page,
                                              @PathVariable(value = "size", required = false) int size){
        ResultService<List<Article>> service = articleService.getArticleByChannelPageSize(channeIdl, page, size);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }
    /**
     *  根据频道获取文章
     */
    @PostMapping("/channel/{columnId}/{page}/{size}")
    public Result getArticleBycolumnIdPageSize(@PathVariable("columnId") String columnId,
                                              @PathVariable(value = "page", required = false) int page,
                                              @PathVariable(value = "size", required = false) int size){
        ResultService<List<Article>> service = articleService.getArticleBycolumnIdPageSize(columnId, page, size);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     * 文章审核
     */
    @PutMapping("/examine/{articleId}")
    public Result check(@PathVariable("articleId") String articleId){
        ResultService<Void> service = articleService.check(articleId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     * 头条文章
     */
    @GetMapping("/top")
    public Result topArticle(){
        ResultService<List<Article>> service = articleService.topArticle();
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

}
