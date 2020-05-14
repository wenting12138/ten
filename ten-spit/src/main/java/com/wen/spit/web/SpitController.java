package com.wen.spit.web;


import com.wen.common.model.Spit;
import com.wen.common.result.PageResult;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import com.wen.common.result.ResultService;
import com.wen.spit.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    /**
     * 保存吐槽
     *
     * @param spit
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Spit spit) {
        ResultService<Void> service = spitService.save(spit);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     * 查询全部列表
     */
    @GetMapping
    public Result findAll() {
        ResultService<List<Spit>> service = spitService.findAll();
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     * 根据id 查询
     */
    @GetMapping("/{spitId}")
    public Result findAll(@PathVariable("spitId") String spitId) {
        ResultService<Spit> service = spitService.findById(spitId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     * 修改吐槽
     */
    @PutMapping("/{spitId}")
    public Result findAll(@PathVariable("spitId") String spitId,
                          @RequestBody Spit spit) {
        ResultService<Void> service = spitService.update(spitId, spit);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  根据id删除吐槽
     */
    @DeleteMapping("/{spitId}")
    public Result del(@PathVariable("spitId") String spitId) {
        ResultService<Void> service = spitService.delById(spitId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  根据条件查询spit列表
     */
    @GetMapping("/search")
    public Result search(@RequestBody Spit spit){
        ResultService<List<Spit>> service = spitService.search(spit);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  根据条件分页查询spit列表
     */
    @PostMapping("/search/{page}/{size}")
    public Result searchPage(@RequestBody(required = false) Spit spit,
                         @PathVariable("page") int page,
                         @PathVariable("size") int size){
        ResultService<PageResult<Spit>> service = spitService.searchPage(spit, page, size);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  根据parentid 查询吐槽数据
     */
    @GetMapping("/comment/{parentId}/{page}/{size}")
    public Result searchPageByParent(@PathVariable("parentId") String parentId ,
                             @PathVariable("page") int page,
                             @PathVariable("size") int size){
        ResultService<PageResult<Spit>> service = spitService.searchPageByParent(parentId, page, size);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, service.getRows());
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  吐槽点赞
     */
    @PutMapping("/thumbup/{spitId}")
    public Result addThumbup(@PathVariable("spitId") String spitId){
        ResultService<Void> service = spitService.addThumbup(spitId);
        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

}
