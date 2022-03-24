package com.company.service;

import com.company.data.dto.request.ChangePasswordRequestDto;
import com.company.data.dto.request.LoginRequestDto;
import com.company.data.dto.request.RegisterRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    void login(LoginRequestDto requestDto);

    ResponseEntity<String> register(RegisterRequestDto requestDto);

    void registerConfirm(String activationCode);

    void resendEmail(Long id);

    void forgetPassword(String email);

    void checkForgetPasswordActivationCode(String forgetPasswordActivationCode);

    void updatePassword(ChangePasswordRequestDto requestDto);

    void foo();
}
