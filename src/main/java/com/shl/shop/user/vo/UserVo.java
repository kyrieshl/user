package com.shl.shop.user.vo;

import com.shl.shop.user.model.User;
import lombok.Data;

import java.util.Date;


@Data
public class UserVo {

    private Integer userId;

    private User seller;

    private Date collectTime;

    public UserVo(){

    }
}
