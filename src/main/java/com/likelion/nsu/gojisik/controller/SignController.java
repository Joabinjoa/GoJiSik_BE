package com.likelion.nsu.gojisik.controller;

//import com.likelion.nsu.gojisik.domain.UserDetails;

import com.likelion.nsu.gojisik.dto.SignUpDto;
import com.likelion.nsu.gojisik.service.SignService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class SignController {


    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;
    private final HttpServletRequest request;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/users")
    public ResponseEntity<SignUpDto> signup(
            @Valid @RequestBody SignUpDto signUpDto
    ) {
        return ResponseEntity.ok(signService.signup(signUpDto));
    }

    @GetMapping("/users")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<SignUpDto> getMyUserInfo( HttpServletRequest request) {
        return ResponseEntity.ok(signService.getMyUserInfo());
    }

    @PutMapping("/users/{phonenum}")
//    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SignUpDto> getUpdateInfo(@PathVariable String phonenum, @RequestBody SignUpDto signUpDto) {
        signService.updateUserInfo(phonenum, signUpDto);
        return ResponseEntity.ok(signUpDto);
    }
}