package com.sardine.user.app.entity.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UserVo implements Serializable {

    /* 手机号 */
    @NotEmpty(message = "用户名不能为空")
    @Pattern(regexp = "^(1[3-9])\\d{9}$", message = "手机号格式有误")
    private String username;

    /* 密码 */
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 32, message = "密码长度必须在4-32位")
    private String password;

    @Length(min = 6, max = 6, message = "验证码必须是6位")
    private String code;
}
