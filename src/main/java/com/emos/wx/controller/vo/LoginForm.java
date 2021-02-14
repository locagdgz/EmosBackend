package com.emos.wx.controller.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("用户登陆")
public class LoginForm {

    @NotBlank(message = "微信临时授权不能为空")
    private String code;

}
