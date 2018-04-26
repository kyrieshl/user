package com.shl.shop.user.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Table(name = "Address")
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer addressId;

    private Integer userId;

    @Length(min = 2,max = 10,message = "收件人长度须在2到10之间！")
    @NotBlank(message = "收件人不能为空！")
    private String recipients;

    @NotBlank(message = "手机号码不能为空！")
    private String phoneNumber;

    @NotBlank(message = "地址不能为空！")
    private String userAddress;

    public Address(){

    }
}
