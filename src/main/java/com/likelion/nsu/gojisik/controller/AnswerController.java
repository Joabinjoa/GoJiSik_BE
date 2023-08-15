//package com.likelion.nsu.gojisik.controller;
//
//import com.likelion.nsu.gojisik.domain.Answer;
//import com.likelion.nsu.gojisik.dto.AnswerRequestDto;
//import com.likelion.nsu.gojisik.dto.AnswerResponseDto;
//import com.likelion.nsu.gojisik.dto.ResponseDto;
//import com.likelion.nsu.gojisik.dto.ResponseStatus;
//import com.likelion.nsu.gojisik.service.AnswerService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/answers")
//public class AnswerController {
//    private final AnswerService answerService;
//
//    // 답변 조회
//    @GetMapping("/{question_id}")
//    public ResponseEntity<?> findAnswers(@PathVariable("question_id") Long questionId){
//        try{
//            List<Answer> answers = answerService.findAnswers(questionId);
//            List<AnswerResponseDto> answerDtos = answers.stream()
//                    .map(AnswerResponseDto::new)
//                    .collect(Collectors.toList());
//
//            ResponseDto<AnswerResponseDto> response = ResponseDto.<AnswerResponseDto>builder()
//                    .status(ResponseStatus.SUCCESS)
//                    .data(answerDtos)
//                    .build();
//            return ResponseEntity.ok().body(response);
//        } catch (Exception e){
//            ResponseDto<AnswerResponseDto> response = ResponseDto.<AnswerResponseDto>builder()
//                    .status(ResponseStatus.FAIL)
//                    .message(e.getMessage())
//                    .build();
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//    // 답변 생성
//    @PostMapping("/{question_id}")
//    public ResponseEntity<?> createAnswer(@AuthenticationPrincipal Long userId,
//                                          @PathVariable("question_id") Long questionId,
//                                          @RequestBody AnswerRequestDto dto){
//        try{
//            Long createdId = answerService.create(userId, questionId, dto);
//            List<Long> result = new ArrayList<>(List.of(createdId));
//
//            ResponseDto<Long> response = ResponseDto.<Long>builder()
//                    .status(ResponseStatus.SUCCESS)
//                    .data(result)
//                    .build();
//            return ResponseEntity.ok().body(response);
//        } catch (Exception e){
//            ResponseDto<AnswerResponseDto> response = ResponseDto.<AnswerResponseDto>builder()
//                    .status(ResponseStatus.FAIL)
//                    .message(e.getMessage())
//                    .build();
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//    // 답변 채택
//    @PutMapping("/{answer_id}")
//    public ResponseEntity<?> adoptAnswer(@AuthenticationPrincipal String userId,
//                                         @PathVariable("answer_id") Long answerId){
//        try{
//            Long adoptedId = answerService.adopt(answerId);
//            List<Long> result = new ArrayList<>(List.of(adoptedId));
//
//            ResponseDto<Long> response = ResponseDto.<Long>builder()
//                    .status(ResponseStatus.SUCCESS)
//                    .data(result)
//                    .build();
//            return ResponseEntity.ok().body(response);
//        } catch (Exception e){
//            ResponseDto<AnswerResponseDto> response = ResponseDto.<AnswerResponseDto>builder()
//                    .status(ResponseStatus.FAIL)
//                    .message(e.getMessage())
//                    .build();
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//    // 답변 내역
//    @GetMapping("/my-answer")
//    public ResponseEntity<?> findAnswersWithUser(@AuthenticationPrincipal Long userId){
//        try{
//            List<Answer> answers = answerService.findByUserId(userId);
//            List<AnswerResponseDto> answerDtos = answers.stream()
//                    .map(AnswerResponseDto::new)
//                    .collect(Collectors.toList());
//
//            ResponseDto<AnswerResponseDto> response = ResponseDto.<AnswerResponseDto>builder()
//                    .status(ResponseStatus.SUCCESS)
//                    .data(answerDtos)
//                    .build();
//            return ResponseEntity.ok().body(response);
//        } catch (Exception e){
//            ResponseDto<AnswerResponseDto> response = ResponseDto.<AnswerResponseDto>builder()
//                    .status(ResponseStatus.FAIL)
//                    .message(e.getMessage())
//                    .build();
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//}
