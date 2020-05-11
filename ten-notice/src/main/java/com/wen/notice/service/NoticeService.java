package com.wen.notice.service;

import com.wen.common.model.Notice;
import com.wen.common.model.NoticeFresh;
import com.wen.common.result.PageResult;
import com.wen.common.result.ResultService;

public interface NoticeService {

    /**
     *  根据id 查询消息通知
     * @param noticeId
     * @return
     */
    ResultService<Notice> findNoticeById(String noticeId);

    /**
     *  新增
     * @param notice
     * @return
     */
    ResultService<Void> save(Notice notice);

    /**
     *  条件分页查询
     * @param notice
     * @param page
     * @param size
     * @return
     */
    ResultService<PageResult<Notice>> searchPage(Notice notice, int page, int size);

    /**
     *  修改
     * @param notice
     * @return
     */
    ResultService<Void> update(Notice notice);

    /**
     *  根据id分页查询待通知消息
     * @param userId
     * @return
     */
    ResultService<PageResult<NoticeFresh>> getByUserId(String userId, int page, int size);

    /**
     *  删除待通知的新消息
     * @return
     */
    ResultService<Void> delNoticeFresh(NoticeFresh noticeFresh);

}
