package com.likelion.nsu.gojisik.controller;

import com.likelion.nsu.gojisik.domain.Question;
import com.likelion.nsu.gojisik.dto.QuestionRequestDto;
import com.likelion.nsu.gojisik.dto.QuestionResponseDto;
import com.likelion.nsu.gojisik.dto.ResponseDto;
import com.likelion.nsu.gojisik.dto.ResponseStatus;
import com.likelion.nsu.gojisik.service.FileService;
import com.likelion.nsu.gojisik.service.QuestionService;
import com.likelion.nsu.gojisik.service.SignService;
import com.likelion.nsu.gojisik.util.SecurityUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
    private final QuestionService questionService;
    private final FileService fileService;
    private final SignService signService;

    // 질문 리스트 조회
    @GetMapping
    public ResponseEntity<?> findQuestions() {
        try {
            List<Question> questions = questionService.findQuestions();
            List<QuestionResponseDto> questionDtos = questions.stream()
                    .map(QuestionResponseDto::new)
                    .collect(Collectors.toList());

            ResponseDto<QuestionResponseDto> response = ResponseDto.<QuestionResponseDto>builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(questionDtos)
                    .build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDto<QuestionResponseDto> response = ResponseDto.<QuestionResponseDto>builder()
                    .status(ResponseStatus.FAIL)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 질문 생성
    @PostMapping
    public ResponseEntity<?> createQuestion(
            @RequestPart(name = "files", required = false) List<MultipartFile> files,
            @RequestPart(name = "dto") QuestionRequestDto dto) {
        try {
            Long createdId = questionService.saveQuestion(signService.getMyUserWithAuthorities().getId(), dto);
            List<Long> result = new ArrayList<>(List.of(createdId));
            fileService.saveFile(createdId, files);

            ResponseDto<Long> response = ResponseDto.<Long>builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(result)
                    .build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDto<QuestionResponseDto> response = ResponseDto.<QuestionResponseDto>builder()
                    .status(ResponseStatus.FAIL)
                    .message(e.toString())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 질문 조회
    @GetMapping("/{question_id}")
    public ResponseEntity<?> finedQuestion(@PathVariable("question_id") Long questionId,
                                           HttpServletRequest req, HttpServletResponse res) {
        try {

            Cookie oldCookie = null;
            Cookie[] cookies = req.getCookies();
            // http 요청의 쿠키가 있다면
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("postView")) {
                        oldCookie = cookie;
                    }
                }
            }

            // 이전 쿠키가 있다면
            if (oldCookie != null) {
                // 값이 [ questionId ] 아니라면
                if (!oldCookie.getValue().contains("["+ questionId.toString() +"]")) {
                    // 조회수 증가
                    this.questionService.updateHits(questionId);
                    oldCookie.setValue(oldCookie.getValue() + "_[" + questionId + "]");
                    oldCookie.setPath("/");
                    oldCookie.setMaxAge(60 * 60 * 24); 							// 쿠키 시간
                    res.addCookie(oldCookie);
                }
                // 이전 쿠기가 없다면
            } else {
                // 조회수 증가
                this.questionService.updateHits(questionId);
                Cookie newCookie = new Cookie("postView", "[" + questionId + "]");
                newCookie.setPath("/");
                newCookie.setMaxAge(60 * 60 * 24); 								// 쿠키 시간
                res.addCookie(newCookie);
            }

            Question question = questionService.findById(questionId);
            QuestionResponseDto dto = new QuestionResponseDto(question);
            List<QuestionResponseDto> result = new ArrayList<>(List.of(dto));

            ResponseDto<QuestionResponseDto> response = ResponseDto.<QuestionResponseDto>builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(result)
                    .build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDto<QuestionResponseDto> response = ResponseDto.<QuestionResponseDto>builder()
                    .status(ResponseStatus.FAIL)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 질문 내역
    @GetMapping("/my-question")
    public ResponseEntity<?> findAnswersWithUser() {
        try {
            Long userid = signService.getMyUserWithAuthorities().getId();
            List<Question> questions = questionService.findByUserId(userid);
            List<QuestionResponseDto> questionDtos = questions.stream()
                    .map(QuestionResponseDto::new)
                    .collect(Collectors.toList());

            ResponseDto<QuestionResponseDto> response = ResponseDto.<QuestionResponseDto>builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(questionDtos)
                    .build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDto<QuestionResponseDto> response = ResponseDto.<QuestionResponseDto>builder()
                    .status(ResponseStatus.FAIL)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
