package com.example.result;

import lombok.Data;

/**
 * ClassName: Reasult
 * Package: com.example.reasult
 * Description:
 *
 * @Author 谢依雯
 * @Create 2024/10/26 15:35
 */
@Data
public class Result {
    private Integer code;
    private Object data;
    private String msg;

    public Result(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
