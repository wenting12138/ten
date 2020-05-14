package com.wen.friend.web;

import com.wen.common.constant.FriendConstant;
import com.wen.common.model.User;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import com.wen.common.result.ResultService;
import com.wen.friend.interceptor.JwtInterceptor;
import com.wen.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    /**
     *  添加喜欢或者不喜欢
     * @return
     */
    @PutMapping("/like/{friendId}/{type}")
    public Result addFriend(@PathVariable("friendId") String friendId,
                            @PathVariable("type") String type){
        User userInfo = JwtInterceptor.getUserInfo();
        String userId = userInfo.getId();
        ResultService<Void> service = null;
        if (type == null || type.equals("")) {
            return Result.ok(ResultCode.PARAMTERERROR, null);
        }
        if (type.equals(FriendConstant.like)) {
            // 表示喜欢
            service = friendService.addFriend(friendId, userId);
        }else if (type.equals(FriendConstant.unlike)) {
            // 表示不喜欢
            service = friendService.addNoFriend(friendId, userId);
        }else {
            return Result.ok(ResultCode.PARAMTERERROR, null);
        }

        if (service.isB()) {
            return Result.ok(ResultCode.SUCCESS, null);
        }
        return Result.ok(ResultCode.FAIL, null);
    }

    /**
     *  删除好友
     */
    @DeleteMapping("/{friendId}")
    public Result del(@PathVariable("friendId") String friendId){
        User userInfo = JwtInterceptor.getUserInfo();
        // 拿到当前用户id
        String userId = userInfo.getId();
        friendService.deleteFriend(friendId, userId);
        return Result.ok(ResultCode.SUCCESS, null);
    }

}
