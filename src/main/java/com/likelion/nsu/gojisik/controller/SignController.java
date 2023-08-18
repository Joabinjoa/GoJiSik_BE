package com.likelion.nsu.gojisik.controller;

//import com.likelion.nsu.gojisik.domain.UserDetails;

import com.likelion.nsu.gojisik.dto.MemberUpdateDto;
import com.likelion.nsu.gojisik.dto.SignUpDto;
import com.likelion.nsu.gojisik.service.SignService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    @PostMapping
    public ResponseEntity<SignUpDto> signup(@Valid @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(signService.signup(signUpDto));
    }

    @GetMapping
    public ResponseEntity<SignUpDto> getMyUserInfo( HttpServletRequest request) {
        return ResponseEntity.ok(signService.getMyUserInfo());
    }

    @PutMapping("/{phonenum}")
    public ResponseEntity<MemberUpdateDto> getUpdateInfo(@PathVariable String phonenum, @RequestBody MemberUpdateDto memberUpdateDto) {
        signService.updateUserInfo(phonenum, memberUpdateDto);
        return ResponseEntity.ok(memberUpdateDto);
    }
}