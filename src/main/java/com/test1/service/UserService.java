package com.test1.service;

import com.test1.domain.dto.token.TokenInfo;
import com.test1.domain.dto.user.UserJoinRequest;
import com.test1.domain.dto.user.UserJoinResponse;
import com.test1.domain.dto.user.UserLoginRequest;
import com.test1.domain.entity.user.User;
import com.test1.exception.ErrorCode;
import com.test1.exception.UserException;
import com.test1.repository.UserRepository;
import com.test1.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public UserJoinResponse join (UserJoinRequest userJoinRequest) {

        // userId 중복 체크
        userRepository.findByUserId(userJoinRequest.getUserId())
                .ifPresent(user -> {
                    throw new UserException(ErrorCode.DUPLICATED_USER_ID);
                });

        User saved = userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));

        return UserJoinResponse.from(saved);

    }

    public boolean checkUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    public TokenInfo login(UserLoginRequest userLoginRequest) {

        String userId = userLoginRequest.getUserId();
        String password = userLoginRequest.getPassword();

        // 해당 id의 user가 있는지 검증
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USERID_NOT_FOUND));

        // id-pw 일치 여부 확인
        if (!encoder.matches(password, user.getPassword())) {
            throw new UserException(ErrorCode.INVALID_PASSWORD);
        }

        // 정상 로그인 실행
        TokenInfo token = jwtUtil.createToken(user.getUserId(), user.getRole(), user.getId());

        return token;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
