package com.company.service;

import com.company.data.dto.request.LoginRequestDto;
import com.company.data.dto.request.RegisterConfirmRequestDto;
import com.company.data.dto.request.RegisterRequestDto;
import com.company.data.dto.request.ResetPasswordRequestDto;
import com.company.data.dto.response.UserResponseDto;
import com.company.resource.JWTAuthResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    JWTAuthResponse login(LoginRequestDto requestDto);
    ResponseEntity<String> register(RegisterRequestDto requestDto);
    void registerConfirm(RegisterConfirmRequestDto requestDto);
    void resendEmail(Long id);
    void forgetPassword(String email);
    void resetPassword(ResetPasswordRequestDto requestDto);
    List<UserResponseDto> showUsersExpectAdmin();
    UserResponseDto findUserRole(Long id);
    void updateUserRoleByUserAndRoleId(Long userId,Long roleId);
}
