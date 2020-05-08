package com.wen.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ResultService<T> {

    private boolean b;

    private Integer total = 0;

    private T rows;

    public ResultService(boolean b){
        this.b = b;
    }
    public ResultService(boolean b, Integer total, T rows){
        this.b = b;
        this.rows = rows;
        this.total = total;
    }
}
