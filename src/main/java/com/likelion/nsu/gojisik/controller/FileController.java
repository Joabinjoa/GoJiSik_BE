package com.likelion.nsu.gojisik.controller;

import com.likelion.nsu.gojisik.domain.File;
import com.likelion.nsu.gojisik.dto.ResponseDto;
import com.likelion.nsu.gojisik.dto.ResponseStatus;
import com.likelion.nsu.gojisik.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private UrlResource handleMalformedURLException(String fileUrl){
        try{
            return new UrlResource(fileUrl);
        }catch (MalformedURLException mue){
            throw new RuntimeException(mue);
        }
    }
}
