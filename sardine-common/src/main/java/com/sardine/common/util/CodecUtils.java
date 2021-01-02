package com.sardine.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 加密解密工具类
 */
public class CodecUtils {

    /**
     * 密码加密 (以用户名作为盐加密)
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static String passwordEncode(String username, String password) {
        return new BCryptPasswordEncoder().encode(username + password);
    }

    /**
     * 密码对比
     *
     * @param rawPassword       原始密码
     * @param encodePassword    密文密码
     * @return
     */
    public static Boolean passwordConfirm(String rawPassword, String encodePassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodePassword);
    }
}
