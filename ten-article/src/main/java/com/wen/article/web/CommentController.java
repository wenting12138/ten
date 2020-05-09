package com.wen.article.web;

import com.wen.article.service.CommentService;
import com.wen.common.model.Comment;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import com.wen.common.result.ResultService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     *  查询所有数据
     */
    @GetMapping("/")
    public Result getAll(){
        ResultService<List<Comment>> service = commentService.getAll();
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  根据id查询评论数据
     */
    @GetMapping("/{commentId}")
    public Result getCommentById(@PathVariable("commentId") String commentId){
        ResultService<Comment> service = commentService.getCommentById(commentId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  新增评论
     */
    @PostMapping
    public Result save(@RequestBody Comment comment){
        ResultService<Comment> service = commentService.save(comment);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }


    /**
     * 修改评论
     */
    @PutMapping("/{commentId}")
    public Result update(@PathVariable("commentId") String commentId, @RequestBody Comment comment) {
        comment.set_id(commentId);
        ResultService<Void> service = commentService.update(comment);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  删除评论
     */
    @DeleteMapping("/{commentId}")
    public Result del(@PathVariable("commentId") String commentId) {
        ResultService<Void> service = commentService.delete(commentId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  根据文章id查询评论
     */
    @GetMapping("/article/{articleId}")
    public Result selectByArticleId(@PathVariable("articleId") String articleId){
        ResultService<List<Comment>> service = commentService.selectByArticleId(articleId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  点赞
     */
    @PutMapping("/thumbup/{commentId}")
    public Result addThumbup(@PathVariable("commentId") String commentId){
        ResultService<Void> service = commentService.addThumbup(commentId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }
}
