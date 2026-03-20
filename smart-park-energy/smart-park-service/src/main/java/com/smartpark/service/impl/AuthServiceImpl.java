package com.smartpark.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.smartpark.common.constant.RedisConstants;
import com.smartpark.common.exception.BusinessException;
import com.smartpark.common.util.EncryptUtils;
import com.smartpark.common.util.IdGenerator;
import com.smartpark.dal.mapper.UserMapper;
import com.smartpark.model.dto.LoginDTO;
import com.smartpark.model.entity.User;
import com.smartpark.model.vo.LoginVO;
import com.smartpark.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public LoginVO login(LoginDTO dto) {
        if (StringUtils.hasText(dto.getCaptcha())) {
            if (!verifyCaptcha(dto.getCaptchaKey(), dto.getCaptcha())) {
                throw new BusinessException("验证码错误");
            }
        }
        
        User user = userMapper.selectByUsername(dto.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        
        String encryptedPassword = EncryptUtils.md5WithSalt(dto.getPassword(), user.getUsername());
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        
        if ("0".equals(user.getStatus())) {
            throw new BusinessException("账号已被禁用");
        }
        
        StpUtil.login(user.getId());
        
        String token = StpUtil.getTokenValue();
        
        LoginVO loginVO = new LoginVO();
        loginVO.setAccessToken(token);
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setRealName(user.getRealName());
        loginVO.setAvatar(user.getAvatar());
        loginVO.setExpiresIn(RedisConstants.TOKEN_EXPIRE_TIME);
        
        List<String> permissions = new ArrayList<>();
        permissions.add("*");
        loginVO.setPermissions(permissions);
        
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        loginVO.setRoles(roles);
        
        String userKey = RedisConstants.USER_PREFIX + user.getId();
        redisTemplate.opsForValue().set(userKey, loginVO, RedisConstants.TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        
        return loginVO;
    }

    @Override
    public void logout() {
        Long userId = StpUtil.getLoginIdAsLong();
        StpUtil.logout();
        
        String userKey = RedisConstants.USER_PREFIX + userId;
        redisTemplate.delete(userKey);
    }

    @Override
    public void refreshToken(String refreshToken) {
        throw new BusinessException("暂不支持刷新Token");
    }

    @Override
    public String getCaptcha() {
        String captchaKey = IdGenerator.uuid();
        String captcha = IdGenerator.randomCode(4);
        
        String key = RedisConstants.CACHE_PREFIX + "captcha:" + captchaKey;
        redisTemplate.opsForValue().set(key, captcha, 300, TimeUnit.SECONDS);
        
        return captchaKey;
    }

    @Override
    public boolean verifyCaptcha(String captchaKey, String captcha) {
        String key = RedisConstants.CACHE_PREFIX + "captcha:" + captchaKey;
        Object storedCaptcha = redisTemplate.opsForValue().get(key);
        
        if (storedCaptcha == null) {
            return false;
        }
        
        boolean result = captcha.equalsIgnoreCase(storedCaptcha.toString());
        if (result) {
            redisTemplate.delete(key);
        }
        
        return result;
    }
}
