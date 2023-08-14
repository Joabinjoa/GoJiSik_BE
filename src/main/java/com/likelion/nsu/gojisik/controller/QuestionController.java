package com.likelion.nsu.gojisik.controller;

import com.likelion.nsu.gojisik.domain.Question;
import com.likelion.nsu.gojisik.dto.RequestDto;
import com.likelion.nsu.gojisik.dto.ResponseDto;
import com.likelion.nsu.gojisik.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<Long> createQuestion(@RequestPart(name = "dto") RequestDto requestDto) {
        try {
            Long questionId = questionService.saveQuestion(requestDto);
            return new ResponseEntity<>(questionId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<ResponseDto>> getQuestionList() {
        try {
            List<ResponseDto> responseDtoList = questionService.getResponseDto();
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{question_id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("question_id") Long questionId) {
        try {
            Optional<Question> question = questionService.findById(questionId);
            return question.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/my-question")
    public ResponseEntity<List<Question>> getMyQuestionList() {
        try {
            // 로그인된 사용자의 ID를 얻어온다고 가정 - 민서가 작성한 코드보고 수정해야 됌
            Long userId = 123L;
            List<Question> questionList = questionService.getMyQuestionList(userId);
            return new ResponseEntity<>(questionList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
