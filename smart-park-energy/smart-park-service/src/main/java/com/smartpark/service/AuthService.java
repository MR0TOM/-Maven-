package com.smartpark.service;

import com.smartpark.model.dto.LoginDTO;
import com.smartpark.model.vo.LoginVO;

public interface AuthService {

    LoginVO login(LoginDTO dto);

    void logout();

    void refreshToken(String refreshToken);

    String getCaptcha();

    boolean verifyCaptcha(String captchaKey, String captcha);
}
