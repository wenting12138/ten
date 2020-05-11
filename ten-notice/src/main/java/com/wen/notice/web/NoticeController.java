package com.wen.notice.web;

import com.wen.common.model.Notice;
import com.wen.common.model.NoticeFresh;
import com.wen.common.result.PageResult;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import com.wen.common.result.ResultService;
import com.wen.notice.service.NoticeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     *  根据id 查询消息通知
     * @param noticeId
     * @return
     */
    @GetMapping("/{noticeId}")
    public Result findNoticeById(@PathVariable("noticeId") String noticeId){
        ResultService<Notice> service = noticeService.findNoticeById(noticeId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    // 2. 根据条件分页查询消息通知
    @PostMapping("/search/{page}/{size}")
    public Result searchPage(@PathVariable("page") int page,
                             @PathVariable("size") int size,
                             @RequestBody(required = false) Notice notice) {
        ResultService<PageResult<Notice>> service = noticeService.searchPage(notice, page, size);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }
    // 3. 新增通知
    @PostMapping
    public Result save(@RequestBody Notice notice){
        if (notice == null) {
            return Result.ok(ResultCode.FAIL, null);
        }
        ResultService<Void> service = noticeService.save(notice);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }
    // 4. 修改通知
    @PutMapping
    public Result update(@RequestBody Notice notice){
        if (notice == null || notice.getId() == null) {
            return Result.ok(ResultCode.FAIL, null);
        }
        ResultService<Void> service = noticeService.update(notice);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }
    // 5. 根据用户id查询该用户的待推送消息
    @GetMapping("/fresh/{userId}/{page}/{size}")
    public Result getByUserId(@PathVariable("userId") String userId,
                              @PathVariable(value = "page", required = false) int page,
                              @PathVariable(value = "size", required = false) int size){
        ResultService<PageResult<NoticeFresh>> service = noticeService.getByUserId(userId, page, size);
        if(service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    // 6. 删除待推送消息(新消息)
    @DeleteMapping("/fresh")
    public Result del(@RequestBody NoticeFresh noticeFresh){
        ResultService<Void> service = noticeService.delNoticeFresh(noticeFresh);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }





}
