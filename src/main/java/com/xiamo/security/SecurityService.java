package com.xiamo.security;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.xiamo.entity.WxUser;
import com.xiamo.model.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The type Security service.
 *
 * @Author: AceXiamo
 * @ClassName: SecurityService
 * @Date: 2023 /3/3 22:46
 */
@Slf4j
@Component
public class SecurityService {

    /**
     * The Secret.
     * 密钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * The Header.
     * 保存Token的Header Name
     */
    @Value("${token.header}")
    private String header;

    /**
     * The Expire time.
     * 过期时间（Min
     */
    @Value("${token.expireTime}")
    private int expireTime;

    /**
     * The Min to refresh.
     * 小于该时间对Token刷新（Min
     */
    @Value("${token.minToRefresh}")
    private int minToRefresh;

    /**
     * The Redis template.
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * The constant LOGIN_USER_KEY.
     */
    private static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * Generate token string.
     *
     * @param userDetails the user details
     * @return the string
     */
    public String generateToken(LoginUser userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String key = IdUtil.fastUUID();
        userDetails.setTokenKey(key);
        saveToCache(key, userDetails);
        claims.put(LOGIN_USER_KEY, key);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * Gets login user.
     *
     * @param request the request
     * @return the login user
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = request.getHeader(header);
        if (StrUtil.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String key = (String) claims.get(LOGIN_USER_KEY);
                LoginUser user = getByCache(key);
                return user;
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * Gets login user.
     *
     * @param token the token
     * @return the login user
     */
    public LoginUser getLoginUser(String token) {
        Claims claims = parseToken(token);
        // 解析对应的权限以及用户信息
        String key = (String) claims.get(LOGIN_USER_KEY);
        LoginUser user = getByCache(key);
        return user;
    }

    /**
     * Refresh token.
     * Token刷新
     *
     * @param user the user
     */
    public void refreshToken(LoginUser user) {
        long expireTime = redisTemplate.getExpire(getCacheKey(user.getTokenKey()));
        if (expireTime <= minToRefresh) {
            saveToCache(user.getTokenKey(), user);
        }
    }

    /**
     * Parse token claims.
     *
     * @param token the token
     * @return the claims
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Save to cache.
     *
     * @param key         the key
     * @param userDetails the user details
     */
    private void saveToCache(String key, UserDetails userDetails) {
        redisTemplate.opsForValue().set(getCacheKey(key), JSON.toJSONString(userDetails), expireTime, TimeUnit.MINUTES);
    }

    /**
     * Gets by cache.
     *
     * @param key the key
     * @return the by cache
     */
    private LoginUser getByCache(String key) {
        String str = redisTemplate.opsForValue().get(getCacheKey(key));
        if (StrUtil.isEmpty(str)) return null;
        return JSON.parseObject(str, LoginUser.class);
    }

    /**
     * Gets cache key.
     *
     * @param key the key
     * @return the cache key
     */
    private String getCacheKey(String key) {
        return LOGIN_USER_KEY + ":" + key;
    }

    /**
     * Gets wx user.
     *
     * @return the wx user
     */
    public static WxUser getWxUser() {
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return user.getWxUser();
        }
        return null;
    }

}
