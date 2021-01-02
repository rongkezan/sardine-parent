package com.sardine.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sardine.common.constants.RedisConstants;
import com.sardine.common.entity.dto.UserDto;
import com.sardine.common.exception.SardineRuntimeException;
import com.sardine.common.util.CodecUtils;
import com.sardine.common.util.JwtUtils;
import com.sardine.common.util.SardineBeanCopyUtils;
import com.sardine.user.entity.domain.User;
import com.sardine.user.entity.vo.UserVo;
import com.sardine.user.mapper.UserMapper;
import com.sardine.user.properties.JwtProperties;
import com.sardine.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 用户服务
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    JwtProperties jwtProperties;

    @Override
    public User selectOne(String username) {
        return userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
    }

    @Override
    public User selectOne(String username, String password) {
        User record = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (record == null || !CodecUtils.passwordConfirm(username + password, record.getPassword()))
            throw new SardineRuntimeException("登录失败，用户名或密码错误");
        return record;
    }

    @Override
    public void insertOne(UserVo userVo, String code) {
        String cacheCode = stringRedisTemplate.opsForValue().get(RedisConstants.USER_VERIFY_PHONE + userVo.getUsername());
        if (!StringUtils.equals(cacheCode, code))
            throw new SardineRuntimeException("手机验证码不正确");
        User record = selectOne(userVo.getUsername());
        if (record != null)
            throw new SardineRuntimeException("该用户名已存在");
        User user = SardineBeanCopyUtils.copyProperties(userVo, User.class);
        String encodePassword = CodecUtils.passwordEncode(userVo.getUsername().trim(), userVo.getPassword().trim());
        user.setPassword(encodePassword)
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
        stringRedisTemplate.delete(RedisConstants.USER_VERIFY_PHONE + userVo.getUsername());
    }

    @Override
    public String identify(String username, String password) {
        User user = selectOne(username, password);
        if (user == null) return null;
        UserDto userDto = new UserDto(user.getId(), user.getUsername());
        return JwtUtils.generateToken(userDto, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
    }
}
