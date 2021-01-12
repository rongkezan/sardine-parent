package com.sardine.user.service;

import com.sardine.user.entity.domain.User;
import com.sardine.user.entity.vo.UserVo;

/**
 * 用户服务
 *
 * @author Keith
 */
public interface UserService {

    /**
     * 根据用户名查询用户
     * @param username  用户名
     * @return
     */
    User selectOne(String username);

    /**
     * 根据用户名密码查询用户
     * @param username  用户名
     * @param password  密码
     */
    User selectOne(String username, String password);

    /**
     * 插入一条记录
     * @param userVo    用户信息
     */
    void insertOne(UserVo userVo);

    /**
     * 验证用户名密码是否合法
     * @param username  用户名
     * @param password  密码
     * @return
     */
    String identify(String username, String password);

    /**
     * 发送手机验证码
     * @param phone 手机号
     */
    void sendCode(String phone);
}
