package com.company.service.impl;

import com.company.config.ModelMapperConfiguration;
import com.company.data.dto.request.LoginRequestDto;
import com.company.data.dto.request.RegisterConfirmRequestDto;
import com.company.data.dto.request.RegisterRequestDto;
import com.company.data.dto.request.ResetPasswordRequestDto;
import com.company.data.dto.response.UserResponseDto;
import com.company.data.entity.Role;
import com.company.data.entity.User;
import com.company.data.repository.RoleRepository;
import com.company.data.repository.UserRepository;
import com.company.data.repository.UserStatusRepository;
import com.company.enums.MessageCase;
import com.company.enums.UserStatusEnum;
import com.company.exception.*;
import com.company.resource.JWTAuthResponse;
import com.company.security.jwt.JwtUtil;
import com.company.service.UserService;
import com.company.utils.ApplicationUtils;
import com.company.utils.MessageUtils;
import com.company.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.company.enums.MessageCase.*;
import static com.company.enums.RoleEnums.ROLE_USER;
import static com.company.utils.StringUtils.parseString;

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
    private final JwtUtil tokenProvider;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    @Override
    public JWTAuthResponse login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmailOrUsername(loginRequestDto.getUsernameOrEmail(), loginRequestDto.getUsernameOrEmail())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND.getMessage()));
        boolean isTrue = passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
        if (!isTrue) {
            throw new WrongPasswordException(WRONG_PASSWORD.getMessage());
        }
        if (user.getStatus().getStatusId().equals(UserStatusEnum.UNCONFIRMED.getStatusId())) {
            throw new UnconfirmedException(USER_UNCONFIRMED.getMessage());
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsernameOrEmail(), loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new JWTAuthResponse(tokenProvider.generateToken(authentication));
    }

    @Transactional
    @Override
    public ResponseEntity<String> register(RegisterRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            return new ResponseEntity<>(MessageCase.USERNAME_ALREADY_TAKEN.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (userRepository.existsByEmail(requestDto.getEmail())) {
            return new ResponseEntity<>(MessageCase.EMAIL_ALREADY_TAKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }
        User user = ModelMapperConfiguration.map(requestDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setExpiredDate(ApplicationUtils.prepareRegistrationExpirationDate());
        user.setActivationCode(ApplicationUtils.getRandomNumberString());
        user.setRole(roleRepository.findRoleByName(ROLE_USER.getRoleName()));
        User saveUser = userRepository.save(user);
        messageUtils.sendAsync(saveUser.getEmail(), messageSubject, messageBody + saveUser.getActivationCode());
        return new ResponseEntity<>(MessageCase.SUCCESSFULLY_REGISTER.getMessage(), HttpStatus.CREATED);
    }

    @Transactional
    @Override
    public void registerConfirm(RegisterConfirmRequestDto requestDto) {
        User user = userRepository.findUserByActivationCode(requestDto.getSixDigitCode());
        if (user.getStatus().getStatusId().equals(UserStatusEnum.CONFIRMED.getStatusId())) {
            throw new AlreadyConfirmedException(MessageCase.USER_ALREADY_CONFIRMED.getMessage());
        }
        Date expiredDate = user.getExpiredDate();
        if (expiredDate.before(currentDate)) {
            throw new ExpirationCodeIsExpiredException(MessageCase.EXPIRATION_TIME_IS_EXPIRED.getMessage());
        } else {
            user.setStatus(userStatusRepository.findUserStatusByStatusId(UserStatusEnum.CONFIRMED.getStatusId()));
            userRepository.save(user);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public void resendEmail(Long id) {
        User user = userRepository.getById(id);
        user.setActivationCode(ApplicationUtils.getRandomNumberString());
        user.setExpiredDate(ApplicationUtils.prepareRegistrationExpirationDate());
        User saveUser = userRepository.save(user);
        messageUtils.sendAsync(saveUser.getEmail(), messageSubject, messageBody + saveUser.getActivationCode());
    }

    @Override
    public void forgetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailIsInCorrectException(MessageCase.EMAIL_IS_INCORRECT.getMessage()));
        user.setSixDigitCode(ApplicationUtils.getRandomNumberString());
        user.setForgetPasswordExpiredDate(ApplicationUtils.prepareForgetPasswordExpirationDate());
        User saveUser = userRepository.save(user);
        messageUtils.sendAsync(saveUser.getEmail(), forgetMessageSubject, forgetMessageBody + saveUser.getSixDigitCode());
    }

    @Override
    public void resetPassword(ResetPasswordRequestDto requestDto) {
        User user = userRepository.findBySixDigitCode(requestDto.getSixDigitCode()).orElseThrow(() -> new SixDigitCodeInCorrectException(SIX_DIGIT_CODE.getMessage()));
        Date expiredDate = user.getForgetPasswordExpiredDate();
        if (expiredDate.before(currentDate)) {
            throw new ExpirationCodeIsExpiredException(MessageCase.EXPIRATION_TIME_IS_EXPIRED.getMessage());
        }
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> showUsersExpectAdmin() {
        List<User> userList = userRepository.findUsersByRoleRoleIdNotLike(2L);
        UserResponseDto userResponseDto = new UserResponseDto();
        userList.forEach((user) -> {
            userResponseDto.setRoleName(user.getRole().getName().substring(user.getRole().getName().lastIndexOf("_") + 1));
            userResponseDto.setStatusName(user.getStatus().getName());
        });
        return userList.stream().map(user -> ModelMapperConfiguration.map(user, userResponseDto)).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto findUserRole(Long id) {
        UserResponseDto responseDto = new UserResponseDto();
        try {
            User user = userRepository.findUserByUserId(id);
            responseDto.setRoleName(user.getRole().getName().substring(user.getRole().getName().lastIndexOf("_") + 1));
        } catch (Exception ex) {
            LOG.error(parseString(ex));
        }
        return responseDto;
    }


    @Override
    public void updateUserRoleByUserAndRoleId(Long userId, Long roleId) {
        try {
            User user = userRepository.findUserByUserId(userId);
            Role role = roleRepository.findRoleByRoleId(roleId);
            user.setRole(role);
            userRepository.save(user);
        } catch (Exception ex) {
            LOG.error(parseString(ex));
        }
    }

}
