package com.wen.base.service.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wen.base.constant.LabelContants;
import com.wen.base.mapper.LabelMapper;
import com.wen.base.repository.LabelRepository;
import com.wen.base.service.LabelService;
import com.wen.common.model.Label;
import com.wen.common.result.PageResult;
import com.wen.common.result.ResultService;
import com.wen.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private IdWorker idWorker;

    @Override
    public ResultService<List<Label>> findAll() {
        List<Label> list = labelRepository.findAll();
        if (list == null || list.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, list.size(), list);
    }

    @Override
    public ResultService<Label> findById(String labelId) {
        Optional<Label> optional = labelRepository.findById(labelId);
        Label label = null;
        if (optional.isPresent()) {
            label = optional.get();
        }
        return new ResultService<>(true, 1, label);
    }

    @Override
    @Transactional
    public ResultService<Void> save(Label label) {
        long id = idWorker.nextId();
        label.setCount(0L);
        label.setFans(0L);
        label.setId(id + "");
        label.setRecommend(LabelContants.recommend);
        label.setState(LabelContants.state);
        labelRepository.save(label);
        return new ResultService<>(true);
    }

    @Override
    @Transactional
    public ResultService<Void> update(Label label, String labelId) {
        label.setId(labelId);
        labelRepository.save(label);
        return new ResultService<>(true);
    }

    @Override
    @Transactional
    public ResultService<Void> deleteById(String labelId) {
        labelRepository.deleteById(labelId);
        return new ResultService<>(true);
    }

    @Override
    public ResultService<PageResult<Label>> searchPage(Label label, int page, int size) {
        if (label == null) {
            label = new Label();
        }else {
            label.setLabelname("%" + label.getLabelname() + "%");
        }
        if (page < 0) {
            page = 1;
        }
        if (size > 100) {
            size = 10;
        }
        PageHelper.startPage(page, size);
        List<Label> labelList = labelMapper.selectByConditionPage(label, page, size);
        if (labelList == null || labelList.isEmpty()) {
            return new ResultService<>(false);
        }
        PageInfo<Label> pageInfo = new PageInfo<>(labelList);
        labelList = pageInfo.getList();
        Long count = pageInfo.getTotal();
        return new ResultService<>(true, labelList.size(), new PageResult<>(count, labelList));
    }

    @Override
    public ResultService<List<Label>> search(Label label) {
        if (label == null) {
            label = new Label();
        }else {
            label.setLabelname("%" + label.getLabelname() + "%");
        }
        List<Label> labelList =  labelMapper.selectByCondition(label);
        if (labelList == null || labelList.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, labelList.size(), labelList);
    }

    @Override
    public ResultService<List<Label>> toplist() {
        List<Label> labelList = labelMapper.selectTopList(LabelContants.recommend);
        if (labelList == null || labelList.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, labelList.size(), labelList);
    }

    @Override
    public ResultService<List<Label>> list() {
        List<Label> labelList = labelMapper.selectList(LabelContants.state);
        if (labelList == null || labelList.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, labelList.size(), labelList);
    }

}
