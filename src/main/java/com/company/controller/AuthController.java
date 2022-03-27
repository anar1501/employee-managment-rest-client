package com.company.controller;

import com.company.data.dto.request.LoginRequestDto;
import com.company.data.dto.request.RegisterConfirmRequestDto;
import com.company.data.dto.request.RegisterRequestDto;
import com.company.data.dto.request.ResetPasswordRequestDto;
import com.company.resource.JWTAuthResponse;
import com.company.security.jwt.JwtUtil;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.company.enums.MessageCase.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(userService.login(requestDto));
    }

    @PostMapping(value = "register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @PostMapping(value = "register-confirm")
    public ResponseEntity<String> registerConfirm(@RequestBody RegisterConfirmRequestDto requestDto) {
        userService.registerConfirm(requestDto);
        return new ResponseEntity<>(REGISTRATION_SUCCESSFULLY_CONFIRMED.getMessage(), HttpStatus.OK);
    }

    @GetMapping(value = "resend/{id}")
    public ResponseEntity<String> resendEmail(@PathVariable(value = "id") Long id) {
        userService.resendEmail(id);
        return ResponseEntity.ok(RESEND_EMAIL_SUCCESSFULLY_SENT.getMessage());
    }

    @PostMapping(value = "forget-password")
    public ResponseEntity<String> forgetPassword(@RequestParam(value = "email") String email) {
        userService.forgetPassword(email);
        return ResponseEntity.ok(PASSWORD_CONFIRMATION_LINK.getMessage());
    }

    @PutMapping(value = "reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDto requestDto) {
        userService.resetPassword(requestDto);
        return ResponseEntity.ok(PASSWORD_SUCCESSFULLY_CHANGED.getMessage());
    }
}
