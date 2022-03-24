package com.company.controller;

import com.company.data.dto.request.ChangePasswordRequestDto;
import com.company.data.dto.request.LoginRequestDto;
import com.company.data.dto.request.RegisterRequestDto;
import com.company.data.entity.User;
import com.company.enums.MessageCase;
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

    @PostMapping(value = "login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto) {
        userService.login(requestDto);
        return new ResponseEntity<>(SUCCESSFULLY_LOGIN.getMessage(), HttpStatus.OK);
    }

    @PostMapping(value = "register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @GetMapping(value = "register-confirm")
    public ResponseEntity<String> registerConfirm(@RequestParam(value = "activationcode") String activationCode) {
        userService.registerConfirm(activationCode);
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

    //change-password
    @GetMapping(value = "check-code")
    public void checkForgetPasswordActivationCode(@RequestParam(value = "activationcode") String activationCode) {
        userService.checkForgetPasswordActivationCode(activationCode);
    }

    @PostMapping(value = "update-password")
    public ResponseEntity<String> updatePassword(@RequestBody ChangePasswordRequestDto requestDto) {
        userService.updatePassword(requestDto);
        return ResponseEntity.ok(PASSWORD_SUCCESSFULLY_CHANGED.getMessage());
    }

}
