package com.shl.shop.user.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@Table(name = "user")
@Entity
public class User implements Serializable{

    private static final long serialVersionUID = 857762387830396365L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Length(min = 2,max = 16,message = "用户名长度须在2到16之间！")
    @NotBlank(message = "用户名不能为空！")
    private String userName;

    @Length(min = 6,max = 15,message = "密码长度须在6到15之间！")
    @NotBlank(message = "密码不能为空！")
    private String password;

    @Email(message = "必须是合法的邮箱！")
    private String email;

    @NotBlank(message = "头像不能为空！")
    private String userImage;

    private Boolean sellerFlag;

    public User(){

    }
}
