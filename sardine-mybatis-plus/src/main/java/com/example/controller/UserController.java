package com.example.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.entity.model.UserDo;
import com.example.manager.UserManager;
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

    @PostMapping("insert")
    public void insert(@RequestBody UserDo user){
        userManager.save(user);
    }

    @GetMapping("select")
    public UserDo select(@RequestParam Long userId){
        return userManager.getOne(Wrappers.<UserDo>lambdaQuery().eq(UserDo::getId, userId));
    }
}

