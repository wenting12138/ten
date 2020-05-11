package com.wen.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    private Long total = 0L;  // 总条数
    private List<T> rows; // 本页总数
}
