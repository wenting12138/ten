package com.wen.common.result;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(20000,true, "成功"),
    FAIL(20001,false, "失败"),
    USERNAMEORPASSWORDERRPR(20002,false, "用户名或密码错误"),
    INSUFFICIENTPERMISSIONS(20003,false, "权限不足"),
    REMOTECALLFAILED(20004, false,"远程调用失败"),
    REPEATTHEOPERATION(20005, false,"重复操作"),
    SUBSCRIBE(20006, true,"订阅成功"),
    UNSUBSCRIBE(20007, true,"取消订阅成功"),
    TOKENERROR(20008, false,"令牌有误"),
    PARAMTERERROR(20009, false,"参数错误"),
    ;

    private int code;
    private boolean flag;
    private String message;

    ResultCode(int code, boolean flag,String message) {
        this.code = code;
        this.message = message;
        this.flag = flag;
    }
}
