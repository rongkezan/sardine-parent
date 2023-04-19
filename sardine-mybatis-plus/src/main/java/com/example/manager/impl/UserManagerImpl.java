package com.example.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.model.UserDo;
import com.example.mapper.UserMapper;
import com.example.manager.UserManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author keith
 * @since 2023-04-19
 */
@Service
public class UserManagerImpl extends ServiceImpl<UserMapper, UserDo> implements UserManager {

}
