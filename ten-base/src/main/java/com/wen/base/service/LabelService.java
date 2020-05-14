package com.wen.base.service;

import com.wen.common.model.Label;
import com.wen.common.result.PageResult;
import com.wen.common.result.ResultService;

import java.util.List;

public interface LabelService {

    /**
     *  查询所有标签
     * @return
     */
    ResultService<List<Label>> findAll();


    /**
     *  根据id查询label
     * @param labelId
     * @return
     */
    ResultService<Label> findById(String labelId);

    /**
     *  保存标签
     * @param label
     * @return
     */
    ResultService<Void> save(Label label);

    /**
     *  根据id修改标签
     * @param label
     * @param labelId
     * @return
     */
    ResultService<Void> update(Label label, String labelId);

    /**
     *  根据id删除
     * @param labelId
     * @return
     */
    ResultService<Void> deleteById(String labelId);

    /**
     *  分页条件查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    ResultService<PageResult<Label>> searchPage(Label label, int page, int size);

    /**
     *  条件查询
     * @param label
     * @return
     */
    ResultService<List<Label>> search(Label label);

    /**
     *  查询推荐列表
     * @return
     */
    ResultService<List<Label>> toplist();

    /**
     * 查询有效标签列表
     * @return
     */
    ResultService<List<Label>> list();

}
