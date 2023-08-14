package com.likelion.nsu.gojisik.controller;

import com.likelion.nsu.gojisik.domain.File;
import com.likelion.nsu.gojisik.domain.Question;
import com.likelion.nsu.gojisik.dto.QuestionRequestDto;
import com.likelion.nsu.gojisik.dto.ResponseDto;
import com.likelion.nsu.gojisik.dto.ResponseStatus;
import com.likelion.nsu.gojisik.repository.QuestionRepository;
import com.likelion.nsu.gojisik.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    private final QuestionRepository questionRepository;

    @GetMapping("/{question_id}")
    public ResponseEntity<?> findFile(@PathVariable("question_id") Long questionId){
        try{
            List<File> files = fileService.findByQuestionId(questionId);
            List<String> fileUrls = new ArrayList<>();
            for(File file : files){
                fileUrls.add("file:" + file.getSavedFileName());
            }
            List<UrlResource> resources = fileUrls.stream()
                    .map(this::handleMalformedURLException)
                    // .map(resource -> "file:///" + resource.getFile())
                    .collect(Collectors.toList());
            ResponseDto<UrlResource> response = ResponseDto.<UrlResource>builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(resources)
                    .build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            ResponseDto response = ResponseDto.builder()
                    .status(ResponseStatus.FAIL)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/{question_id}")
    public ResponseEntity<?> saveFile(@AuthenticationPrincipal Long userId,
                                      @PathVariable("question_id") Long questionId,
                                      @RequestPart("files") List<MultipartFile> files){
        try {
            Question question = questionRepository.findById(questionId).get();
            fileService.saveFile(question, files);

            List<Long> result = new ArrayList<>(List.of(question.getId()));
            ResponseDto<Long> response = ResponseDto.<Long>builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(result)
                    .build();
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            ResponseDto response = ResponseDto.builder()
                    .status(ResponseStatus.FAIL)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 테스트용
    @PostMapping
    public ResponseEntity<?> saveFile(@AuthenticationPrincipal Long userId,
                                      @RequestPart("dto") QuestionRequestDto dto,
                                      @RequestPart("files") List<MultipartFile> files){
        try {
            Question question = QuestionRequestDto.toEntity(dto);
            Question saved = questionRepository.save(question);
            fileService.saveFile(saved, files);

            List<Long> result = new ArrayList<>(List.of(saved.getId()));
            ResponseDto<Long> response = ResponseDto.<Long>builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(result)
                    .build();
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            ResponseDto response = ResponseDto.builder()
                    .status(ResponseStatus.FAIL)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    private UrlResource handleMalformedURLException(String fileUrl){
        try{
            return new UrlResource(fileUrl);
        }catch (MalformedURLException mue){
            throw new RuntimeException(mue);
        }
    }
}
