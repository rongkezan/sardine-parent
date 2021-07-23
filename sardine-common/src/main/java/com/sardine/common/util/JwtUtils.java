package com.sardine.common.util;

import com.sardine.common.constants.JwtConstants;
import com.sardine.common.entity.domain.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Jwt工具类
 */
public class JwtUtils {
    /**
     * 私钥加密token
     *
     * @param userDto       载荷中的数据
     * @param privateKey    私钥
     * @param expireMinutes 过期时间，单位秒
     * @return
     */
    public static String generateToken(UserDto userDto, PrivateKey privateKey, int expireMinutes) {
        return Jwts.builder()
                .claim(JwtConstants.JWT_KEY_ID, userDto.getId())
                .claim(JwtConstants.JWT_KEY_USER_NAME, userDto.getUsername())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(expireMinutes).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 私钥加密token
     *
     * @param userDto       载荷中的数据
     * @param privateKey    私钥字节数组
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(UserDto userDto, byte[] privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim(JwtConstants.JWT_KEY_ID, userDto.getId())
                .claim(JwtConstants.JWT_KEY_USER_NAME, userDto.getUsername())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(expireMinutes).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.RS256, RsaUtils.getPrivateKey(privateKey))
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥字节数组
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, byte[] publicKey) throws Exception {
        return Jwts.parser().setSigningKey(RsaUtils.getPublicKey(publicKey))
                .parseClaimsJws(token);
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static UserDto getInfoFromToken(String token, PublicKey publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserDto(
                ObjectUtils.toLong(body.get(JwtConstants.JWT_KEY_ID)),
                ObjectUtils.toString(body.get(JwtConstants.JWT_KEY_USER_NAME))
        );
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static UserDto getInfoFromToken(String token, byte[] publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserDto(
                ObjectUtils.toLong(body.get(JwtConstants.JWT_KEY_ID)),
                ObjectUtils.toString(body.get(JwtConstants.JWT_KEY_USER_NAME))
        );
    }

    private static class ObjectUtils {
        public static String toString(Object obj) {
            if (obj == null) {
                return null;
            }
            return obj.toString();
        }

        public static Long toLong(Object obj) {
            if (obj == null) {
                return 0L;
            }
            if (obj instanceof Double || obj instanceof Float) {
                return Long.valueOf(StringUtils.substringBefore(obj.toString(), "."));
            }
            if (obj instanceof Number) {
                return Long.valueOf(obj.toString());
            }
            if (obj instanceof String) {
                return Long.valueOf(obj.toString());
            } else {
                return 0L;
            }
        }

        public static Integer toInt(Object obj) {
            return toLong(obj).intValue();
        }
    }
}