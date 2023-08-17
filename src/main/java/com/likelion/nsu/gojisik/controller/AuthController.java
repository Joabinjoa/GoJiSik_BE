package com.likelion.nsu.gojisik.controller;

//import com.likelion.nsu.gojisik.dto.SignInDto;

import com.likelion.nsu.gojisik.dto.SignInDto;
import com.likelion.nsu.gojisik.dto.TokenDto;
import com.likelion.nsu.gojisik.security.JwtFilter;
import com.likelion.nsu.gojisik.security.TokenProvider;
import com.likelion.nsu.gojisik.service.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/users/login")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody SignInDto signInDto) {
//        signInDto.phone(signInDto.getUsername(), signInDto.getPhonenum());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInDto.getPhonenum(), signInDto.getPassword());
        LOGGER.info("authenticationToken : {}", authenticationToken);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        LOGGER.info("asdf auth :{}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LOGGER.info("asdf auth :{}", authentication);
        String jwt = tokenProvider.createToken((authentication));


        LOGGER.info("asdf auth :{}", jwt);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt, authenticationToken.getName()), httpHeaders, HttpStatus.OK);
    }

//    @GetMapping("/user3")
//    public String getCurrentUser() {
//
//
//           Object username = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//            // Member 객체 가져오기
////            Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
//
//
//            return "Current User: " + username ;
//        }

}

