package com.likelion.nsu.gojisik.service;

//import com.likelion.nsu.gojisik.config.CommonResponse;
import com.likelion.nsu.gojisik.domain.Authority;
import com.likelion.nsu.gojisik.domain.User;
import com.likelion.nsu.gojisik.dto.*;
import com.likelion.nsu.gojisik.exception.DuplicateMemberException;
import com.likelion.nsu.gojisik.exception.NotFoundMemberException;
import com.likelion.nsu.gojisik.repository.UserRepository;
//import com.likelion.nsu.gojisik.security.JwtTokenProvider;
import com.likelion.nsu.gojisik.security.TokenProvider;
import com.likelion.nsu.gojisik.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Service
public class SignService {
    private final Logger LOGGER = LoggerFactory.getLogger(SignService.class);


    public TokenProvider tokenProvider;
    public PasswordEncoder passwordEncoder;
    public UserRepository userRepository;

    @Autowired
    public SignService(UserRepository userRepository, TokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입
    @Transactional
    public SignUpDto signup(SignUpDto signUpDto) {
        if (userRepository.findOneWithAuthoritiesByPhonenum(signUpDto.getPhonenum()).orElse(null) != null) {
            LOGGER.info("이미 가입되어 있는 유저입니다");
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");

        }


        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .phonenum(signUpDto.getPhonenum())
                .birthday(signUpDto.getBirthday())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return SignUpDto.from(userRepository.save(user));
    }


    @Transactional(readOnly = true)

    public SignUpDto getUserWithAuthorities(String phonenum) {
        return SignUpDto.from(userRepository.findOneWithAuthoritiesByPhonenum(phonenum).orElse(null));
    }

    @Transactional(readOnly = true)
    public SignUpDto getMyUserWithAuthorities() {
        return SignUpDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByPhonenum)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

    public User updateUserInfo(String phonenum, SignUpDto signUpDto) {
        User user = userRepository.findOneWithAuthoritiesByPhonenum(phonenum)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + phonenum));

        // 업데이트할 정보를 updateInfo로부터 받아와서 member 엔터티에 반영
        user.setPhonenum(signUpDto.getPhonenum());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setBirthday(signUpDto.getBirthday());
        user.setFont(signUpDto.getFont());
        user.setPassword(signUpDto.getPassword());

        return userRepository.save(user);
    }
}

