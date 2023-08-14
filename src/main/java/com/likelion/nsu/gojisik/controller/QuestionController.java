package com.likelion.nsu.gojisik.controller;

import com.likelion.nsu.gojisik.domain.Question;
import com.likelion.nsu.gojisik.dto.ResponseStatus;
import com.likelion.nsu.gojisik.dto.*;
import com.likelion.nsu.gojisik.service.FileService;
import com.likelion.nsu.gojisik.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final FileService fileService;

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
            ResponseDto<AnswerResponseDto> response = ResponseDto.<AnswerResponseDto>builder()
                    .status(ResponseStatus.FAIL)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 질문 생성
    @PostMapping
    public ResponseEntity<?> createQuestion(@AuthenticationPrincipal Long userId,
                                            @RequestPart(name = "files") List<MultipartFile> files,
                                            @RequestPart(name = "dto") QuestionRequestDto dto) {
        try {
            Long createdId = questionService.saveQuestion(userId, dto);
            List<Long> result = new ArrayList<>(List.of(createdId));
            fileService.saveFile(createdId, files);

            ResponseDto<Long> response = ResponseDto.<Long>builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(result)
                    .build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDto<AnswerResponseDto> response = ResponseDto.<AnswerResponseDto>builder()
                    .status(ResponseStatus.FAIL)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 질문 조회
    @GetMapping("/{question_id}")
    public ResponseEntity<?> finedQuestion(@PathVariable("question_id") Long questionId) {
        try {
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
    public ResponseEntity<?> findAnswersWithUser(@AuthenticationPrincipal Long userId) {
        try {
            List<Question> questions = questionService.findByUserId(userId);
            List<QuestionResponseDto> questionDtos = questions.stream()
                    .map(QuestionResponseDto::new)
                    .collect(Collectors.toList());

            ResponseDto<QuestionResponseDto> response = ResponseDto.<QuestionResponseDto>builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(questionDtos)
                    .build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDto<AnswerResponseDto> response = ResponseDto.<AnswerResponseDto>builder()
                    .status(ResponseStatus.FAIL)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
