package com.company.service.impl;

import com.company.config.ModelMapperConfiguration;
import com.company.controller.AuthController;
import com.company.data.dto.request.ChangePasswordRequestDto;
import com.company.data.dto.request.LoginRequestDto;
import com.company.data.dto.request.RegisterRequestDto;
import com.company.data.dto.request.ResetPasswordRequestDto;
import com.company.data.entity.User;
import com.company.data.repository.UserRepository;
import com.company.data.repository.UserStatusRepository;
import com.company.enums.MessageCase;
import com.company.enums.UserStatusEnum;
import com.company.exception.*;
import com.company.service.UserService;
import com.company.utils.GeneralUtils;
import com.company.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

import static com.company.enums.MessageCase.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private final static Date currentDate = new Date();


    @Value("${my.message.subject}")
    private String messageSubject;

    @Value("${my.message.body}")
    private String messageBody;


    @Value("${my.message.forget-subject}")
    private String forgetMessageSubject;

    @Value("${my.message.forget-body}")
    private String forgetMessageBody;

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MessageUtils messageUtils;

    @Transactional
    @Override
    public void login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmailOrUsername(loginRequestDto.getUsernameOrEmail(), loginRequestDto.getUsernameOrEmail())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND.getMessage()));
        boolean isTrue = passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
        if (!isTrue) {
            throw new WrongPasswordException(WRONG_PASSWORD.getMessage());
        }
        if (user.getStatus().getId().equals(UserStatusEnum.UNCONFIRMED.getStatusId())) {
            throw new UnconfirmedException(USER_UNCONFIRMED.getMessage());
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsernameOrEmail(), loginRequestDto.getPassword())));
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
        user.setExpiredDate(GeneralUtils.prepareRegistrationExpirationDate());
        user.setActivationCode(passwordEncoder.encode(UUID.randomUUID().toString()));
        userRepository.save(user);
        User saveUser = userRepository.save(user);
        String confirmLink = "http://localhost:8080/api/v1/auth/register-confirm?activationcode=" + saveUser.getActivationCode();
        messageUtils.sendAsync(saveUser.getEmail(), messageSubject, messageBody + confirmLink);
        return new ResponseEntity<>(MessageCase.SUCCESSFULLY_REGISTER.getMessage(), HttpStatus.CREATED);
    }

    @Transactional
    @Override
    public void registerConfirm(String activationCode) {
        User user = userRepository.findByActivationCode(activationCode);
        if (user.getStatus().getId().equals(UserStatusEnum.CONFIRMED.getStatusId())) {
            throw new AlreadyConfirmedException(MessageCase.USER_ALREADY_CONFIRMED.getMessage());
        }
        Date expiredDate = user.getExpiredDate();
        if (expiredDate.before(currentDate)) {
            throw new ExpirationCodeIsExpiredException(MessageCase.EXPIRATION_TIME_IS_EXPIRED.getMessage());
        } else {
            user.setStatus(userStatusRepository.findUserStatusById(UserStatusEnum.CONFIRMED.getStatusId()));
            userRepository.save(user);
        }

    }

    @Transactional
    @Override
    public void resendEmail(Long id) {
        User user = userRepository.getById(id);
        user.setActivationCode(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.setExpiredDate(GeneralUtils.prepareRegistrationExpirationDate());
        User saveUser = userRepository.save(user);
        String confirmLink = "http://localhost:8080/api/v1/auth/register-confirm?activationcode=" + saveUser.getActivationCode();
        messageUtils.sendAsync(saveUser.getEmail(), messageSubject, messageBody + confirmLink);
    }

    @Override
    public void forgetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailIsInCorrectException(MessageCase.EMAIL_IS_INCORRECT.getMessage()));
        user.setSixDigitCode(GeneralUtils.getRandomNumberString());
        user.setForgetPasswordExpiredDate(GeneralUtils.prepareForgetPasswordExpirationDate());
        User saveUser = userRepository.save(user);
        messageUtils.sendAsync(saveUser.getEmail(), forgetMessageSubject, forgetMessageBody + saveUser.getSixDigitCode());
    }

    @Override
    public void resetPassword(ResetPasswordRequestDto requestDto) {
        //Todo:codun duzgun olub olmadigini yoxlamaliyam
        User user = userRepository.findBySixDigitCode(requestDto.getSixDigitCode()).orElseThrow(()->new SixDigitCodeInCorrectException(SIX_DIGIT_CODE.getMessage()));
        Date expiredDate = user.getForgetPasswordExpiredDate();
        if (expiredDate.before(currentDate)) {
            throw new ExpirationCodeIsExpiredException(MessageCase.EXPIRATION_TIME_IS_EXPIRED.getMessage());
        }
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);
    }

}
