package com.sardine.user.app.controller;

import com.sardine.common.entity.domain.UserDto;
import com.sardine.common.entity.http.Result;
import com.sardine.common.entity.http.Results;
import com.sardine.common.exception.BusinessException;
import com.sardine.common.util.JwtUtils;
import com.sardine.user.app.api.UserApi;
import com.sardine.user.app.entity.vo.UserVo;
import com.sardine.user.app.properties.JwtProperties;
import com.sardine.user.app.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * 用户控制层
 *
 * @author Keith
 */
@Slf4j
@RestController
@Api(tags = "用户管理相关接口")
public class UserController implements UserApi {

    private final UserService userService;

    private final JwtProperties jwtProperties;

    public UserController(UserService userService, JwtProperties jwtProperties) {
        this.userService = userService;
        this.jwtProperties = jwtProperties;
    }

    @PostMapping("login")
    @ApiOperation("登录")
    public Result<String> login(@Valid @RequestBody UserVo userVo, BindingResult result) {
        if (result.hasFieldErrors())
            throw BusinessException.of(result.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("|")));
        String token = userService.identify(userVo.getUsername(), userVo.getPassword());
        if (StringUtils.isBlank(token))
            return Results.failed("身份未认证", "");
        return Results.success("登录成功", token);
    }

    @PostMapping("signup")
    @ApiOperation("注册")
    public Result<Boolean> signup(@Valid @RequestBody UserVo userVo, BindingResult result){
        if (result.hasFieldErrors())
            throw BusinessException.of(result.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("|")));
        userService.insertOne(userVo);
        return Results.success("用户注册成功");
    }

    @PostMapping("code")
    @ApiOperation("发送手机验证码")
    public Result<Void> code(String phone){
        userService.sendCode(phone);
        return Results.success();
    }

    @PostMapping("identify")
    @ApiOperation("解析Token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌")
    })
    public Result<UserDto> identify(@RequestParam String token) {
        UserDto userInfo = null;
        try {
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            return Results.failed(null);
        }
        return Results.success(userInfo);
    }
}
