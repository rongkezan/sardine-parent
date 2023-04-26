package com.sardine.mybatis.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sardine.mybatis.entity.model.UserDo;
import com.sardine.mybatis.manager.UserManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author keith
 * @since 2023-04-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserManager userManager;

    @GetMapping("insert")
    public void insert(){
        userManager.save(new UserDo());
    }

    @GetMapping("select")
    public UserDo select(@RequestParam Long userId){
        return userManager.getOne(Wrappers.<UserDo>lambdaQuery().eq(UserDo::getId, userId));
    }
}

