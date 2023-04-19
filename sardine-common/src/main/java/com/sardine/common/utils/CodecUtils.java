package com.sardine.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 加密解密工具类
 *
 * @author keith
 */
public class CodecUtils {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 密码加密 (以用户名作为盐加密)
     *
     * @param username 用户名
     * @param password 密码
     * @return  加密后的密码
     */
    public static String passwordEncode(String username, String password) {
        return ENCODER.encode(username + password);
    }

    /**
     * 密码对比
     *
     * @param username          用户名
     * @param password          原始密码
     * @param encodePassword    密文密码
     * @return  true比对成功，false比对失败
     */
    public static boolean passwordMatch(String username, String password, String encodePassword) {
        return ENCODER.matches(username + password, encodePassword);
    }
}
