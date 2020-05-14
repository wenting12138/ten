package com.wen.spit.service;

import com.wen.common.model.Spit;
import com.wen.common.result.PageResult;
import com.wen.common.result.ResultService;
import com.wen.common.utils.IdWorker;
import com.wen.spit.repository.SpitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SpitService {

    @Autowired
    private SpitRepository spitRepository;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public ResultService<Void> save(Spit spit) {
        String id = idWorker.nextId() + "";
        spit.set_id(id);
        spit.setComment(0);
        spit.setPublishTime(new Date());
        spit.setThumbup(0);
        spit.setVisits(0);
        // TODO userId
        String userId = "1";
        spit.setUserId(userId);
        if (spit.getParentId() != null && !spit.getParentId().equals("")){
            // 对父节点的回复数 +1
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentId()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitRepository.save(spit);
        return new ResultService<>(true);
    }

    public ResultService<List<Spit>> findAll() {
        List<Spit> spitList = spitRepository.findAll();
        if (spitList == null || spitList.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, spitList.size(), spitList);
    }

    public ResultService<Spit> findById(String spitId) {
        Optional<Spit> optional = spitRepository.findById(spitId);
        if (optional.isPresent()) {
            return new ResultService<>(true, 1, optional.get());
        }
        return new ResultService<>(false);
    }

    public ResultService<Void> update(String spitId, Spit spit) {

        if (spit == null) {
            return new ResultService<>(false);
        }
        spit.set_id(spitId);
        spitRepository.save(spit);
        return new ResultService<>(true);
    }

    public ResultService<Void> delById(String spitId) {
        spitRepository.deleteById(spitId);
        return new ResultService<>(true);
    }

    public ResultService<List<Spit>> search(Spit spit) {
        Example<Spit> example = Example.of(spit);
        List<Spit> spitList = spitRepository.findAll(example);
        if (spitList == null || spitList.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, spitList.size(), spitList);
    }

    public ResultService<PageResult<Spit>> searchPage(Spit spit, int page, int size) {
        if (spit == null) {
            spit = new Spit();
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        // 自定义匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher.withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains());
        exampleMatcher.withMatcher("nickName", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Spit> example = Example.of(spit, exampleMatcher);
        Page<Spit> spitPage = spitRepository.findAll(example, pageable);
        if (spitPage == null) {
            return new ResultService<>(false);
        }
        List<Spit> spitList = spitPage.getContent();
        Long count = spitPage.getTotalElements();
        return new ResultService<>(true, spitList.size(), new PageResult<>(count, spitList));
    }

    public ResultService<PageResult<Spit>> searchPageByParent(String parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Spit> spitPage = spitRepository.findByParentId(parentId, pageable);
        if (spitPage == null) {
            return new ResultService<>(false);
        }
        List<Spit> spitList = spitPage.getContent();
        Long count = spitPage.getTotalElements();
        return new ResultService<>(true, spitList.size(), new PageResult<>(count, spitList));
    }

    private static final String addThumbup = "spit_addThumbup_";

    // 点赞
    public ResultService<Void> addThumbup(String spitId) {
//        Optional<Spit> optional = spitRepository.findById(spitId);
//        if (optional.isPresent()) {
//            Spit spit = optional.get();
//            spit.setThumbup(spit.getThumbup() + 1);
//            spitRepository.save(spit);
//        }
        // TODO userId
        String userId = "1";
        String v = redisTemplate.opsForValue().get(addThumbup + userId);

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        if (v == null) {
            // 表示没有点赞
            // redis添加
            redisTemplate.opsForValue().set("addThumbup" + userId, "1");
            update.inc("thumbup", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }else {
            // 表示已点过赞 取消点赞
            redisTemplate.delete(addThumbup + userId);
            update.inc("thumbup", -1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        return new ResultService<>(true);
    }
}
