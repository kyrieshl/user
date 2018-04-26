package com.shl.shop.user.result;

import lombok.Data;

@Data
public class Result {
    private Integer code;

    private String msg;

    private Object data;
}
