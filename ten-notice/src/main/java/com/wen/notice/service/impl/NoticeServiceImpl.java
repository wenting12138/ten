package com.wen.notice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import com.wen.common.model.Notice;
import com.wen.common.model.NoticeFresh;
import com.wen.common.model.User;
import com.wen.common.result.PageResult;
import com.wen.common.result.Result;
import com.wen.common.result.ResultService;
import com.wen.common.utils.IdWorker;
import com.wen.notice.constants.NoticeConstants;
import com.wen.notice.dao.NoticeFreshMapper;
import com.wen.notice.dao.NoticeMapper;
import com.wen.notice.feignclient.ArticleClient;
import com.wen.notice.feignclient.UserClient;
import com.wen.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private NoticeFreshMapper noticeFreshMapper;

    @Autowired
    private ArticleClient articleClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private IdWorker idWorker;

    @Override
    public ResultService<Notice> findNoticeById(String noticeId) {
        Notice notice = noticeMapper.findOneById(noticeId);
        if (notice == null) {
            return new ResultService<>(false);
        }
        // 调用feign 完善信息
        getUserInfo(notice);
        return new ResultService<>(true, 1, notice);
    }

    @Override
    @Transactional
    public ResultService<Void> save(Notice notice) {
        // 设置消息未读
        notice.setState(NoticeConstants.UNREAD);
        // 设置时间
        notice.setCreateTime(new Date());
        // 设置id
        String id = idWorker.nextId() + "";
        notice.setId(id);
        // 待推送消息的入库
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setNoticeId(id);
        // 待通知消息的用户的id   消息的接收方的id
        noticeFresh.setUserId(notice.getReceiverId());
        noticeFreshMapper.save(noticeFresh);
        noticeMapper.save(notice);
        return new ResultService<>(true);
    }

    @Override
    public ResultService<PageResult<Notice>> searchPage(Notice notice, int page, int size) {
        if (notice == null) {
            notice = new Notice();
        }
        if (page < 0) {
            page = 1;
        }
        if (size > 100) {
            size = 20;
        }
        PageHelper.startPage(page, size);
        List<Notice> result = noticeMapper.selectPageCondition(notice, page, size);
        if (result == null || result.isEmpty()) {
            return new ResultService<>(false);
        }
        PageInfo<Notice> pageInfo = new PageInfo<>(result);
        Long total = pageInfo.getTotal();
        result = pageInfo.getList();
        for (Notice n : result) {
            // 调用feign 完善信息
            getUserInfo(n);
        }
        return new ResultService<>(true, 1, new PageResult<>(total, result));
    }

    @Override
    @Transactional
    public ResultService<Void> update(Notice notice) {
        noticeMapper.update(notice);
        return new ResultService<>(true);
    }

    @Override
    public ResultService<PageResult<NoticeFresh>> getByUserId(String userId, int page, int size) {
        if (page < 0) {
            page = 1;
        }
        if (size > 100) {
            size = 20;
        }
        PageHelper.startPage(page, size);
        List<NoticeFresh> noticeFreshes = noticeFreshMapper.findByUserPage(userId, page, size);
        if (noticeFreshes == null || noticeFreshes.isEmpty()) {
            return new ResultService<>(false);
        }
        PageInfo<NoticeFresh> pageInfo = new PageInfo<>(noticeFreshes);
        noticeFreshes = pageInfo.getList();
        Long count = pageInfo.getTotal();
        return new ResultService<>(true, 1, new PageResult<>(count, noticeFreshes));
    }

    @Override
    @Transactional
    public ResultService<Void> delNoticeFresh(NoticeFresh noticeFresh) {
        noticeFreshMapper.delNoticeFresh(noticeFresh);
        return new ResultService<>(true);
    }

    // 完善消息内容
    private void getUserInfo(Notice notice) {
        Result user = userClient.getOneUser(notice.getOperatorId());
        Map<String, Object> map = (Map<String, Object>) user.getData();
        // 设置操作者的用户昵称
        Object nickname = map.get("nickname");
        if (nickname != null) {
            notice.setOperatorName(nickname.toString());
        }

        // 设置文章标题
        Result result = articleClient.selectOne(notice.getTargetId());
        Map<String, Object> m = (Map<String, Object>) result.getData();
        if (m != null && m.get("title") != null) {
            notice.setTargetName(m.get("title").toString());
        }
    }

}
