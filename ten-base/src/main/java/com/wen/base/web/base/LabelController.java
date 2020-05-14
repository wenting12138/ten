package com.wen.base.web.base;

import com.sun.org.apache.regexp.internal.RE;
import com.wen.base.service.LabelService;
import com.wen.common.model.Label;
import com.wen.common.result.PageResult;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import com.wen.common.result.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     *  查询全部标签
     * @return
     */
    @GetMapping
    public Result findAll(){
        ResultService<List<Label>> service = labelService.findAll();
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows() );
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  根据id查询label标签
     * @param labelId
     * @return
     */
    @GetMapping("/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId){
        ResultService<Label> service = labelService.findById(labelId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows() );
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  保存
     * @param label
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Label label){
        ResultService<Void> service = labelService.save(label);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  修改
     * @param label
     * @param labelId
     * @return
     */
    @PutMapping("/{labelId}")
    public Result update(@RequestBody Label label, @PathVariable("labelId") String labelId){
        ResultService<Void> service = labelService.update(label, labelId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  删除
     * @param labelId
     * @return
     */
    @DeleteMapping("/{labelId}")
    public Result deleteById(@PathVariable("labelId") String labelId){
        ResultService<Void> service = labelService.deleteById(labelId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  分页条件查询
     */
    @PostMapping("/search/{page}/{size}")
    public Result searchPage(@PathVariable("page") int page,
                             @PathVariable("size") int size,
                             @RequestBody(required = false) Label label){
        ResultService<PageResult<Label>> service = labelService.searchPage(label, page, size);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  分页查询
     * @param label
     * @return
     */
    @PostMapping("/search")
    public Result search(@RequestBody Label label){

        ResultService<List<Label>> service = labelService.search(label);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  查询推荐列表:
     */
    @GetMapping("/toplist")
    public Result toplist(){
        ResultService<List<Label>> service = labelService.toplist();
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  查询有效标签列表
     */
    @GetMapping("/list")
    public Result list(){
        ResultService<List<Label>> service = labelService.list();
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }
}
