package com.itCar.base.api.wxApi.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: RegisterVo 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/30 9:57
 * @Version: v1.0
 */
@Data
public class RegisterVo {

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
