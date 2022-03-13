package com.company.service.impl;

import com.company.config.ModelMapperConfiguration;
import com.company.data.dto.request.LoginRequestDto;
import com.company.data.dto.request.RegisterRequestDto;
import com.company.data.entity.User;
import com.company.data.repository.UserRepository;
import com.company.enums.MessageCase;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void login(LoginRequestDto requestDto) {
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsernameOrEmail(), requestDto.getPassword())));
    }

    @Override
    public ResponseEntity<String> register(RegisterRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            return new ResponseEntity<>(MessageCase.USERNAME_ALREADY_TAKEN.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (userRepository.existsByEmail(requestDto.getEmail())) {
            return new ResponseEntity<>(MessageCase.EMAIL_ALREADY_TAKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }
        User user = ModelMapperConfiguration.map(requestDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(MessageCase.SUCCESSFULLY_REGISTER.getMessage(), HttpStatus.CREATED);
    }
}
