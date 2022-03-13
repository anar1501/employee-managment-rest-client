package com.company.controller;

import com.company.data.dto.request.LoginRequestDto;
import com.company.data.dto.request.RegisterRequestDto;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.company.enums.MessageCase.SUCCESSFULLY_LOGIN;

@RestController
@RequestMapping("/api/auth/v1")
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

}
