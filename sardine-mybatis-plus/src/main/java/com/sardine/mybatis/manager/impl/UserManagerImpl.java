package com.sardine.mybatis.manager.impl;

import com.sardine.mybatis.entity.model.UserDo;
import com.sardine.mybatis.mapper.UserMapper;
import com.sardine.mybatis.manager.UserManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
