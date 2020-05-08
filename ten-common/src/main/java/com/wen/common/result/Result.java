package com.wen.common.result;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@ToString
public class Result {

    // 是否成功
    private Boolean flag;

    // 信息
    private String message;

    // 返回码
    private Integer code;

    // 数据
    private Object data;

    public Result(Boolean flag, String message, Integer code) {
        this.flag = flag;
        this.message = message;
        this.code = code;
    }

    public Result(Boolean flag, String message, Integer code, Object data) {
        this.flag = flag;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static Result ok(ResultCode code, Object data){
        Result result = new Result();
        result.setFlag(code.isFlag());
        result.setCode(code.getCode());
        result.setMessage(code.getMessage());
        result.setData(data);
        return result;
    }
}
