package com.likelion.nsu.gojisik.controller;

import com.likelion.nsu.gojisik.domain.File;
import com.likelion.nsu.gojisik.domain.FileType;
import com.likelion.nsu.gojisik.dto.ResponseDto;
import com.likelion.nsu.gojisik.dto.ResponseStatus;
import com.likelion.nsu.gojisik.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @GetMapping("/image/{question_id}")
    public ResponseEntity<?> findFile(@PathVariable("question_id") Long questionId){
        try{
            List<File> files = fileService.findByQuestionId(questionId);
            List<String> imageUrl = files.stream()
                    .filter(file -> file.getType() == FileType.Image)
                    .map(file -> "file:" + file.getSavedFileName())
                    .toList();
            UrlResource resource = new UrlResource(imageUrl.get(0));
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
        }catch (Exception e){
            ResponseDto response = ResponseDto.builder()
                    .status(ResponseStatus.FAIL)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/audio/{question_id}")
    public ResponseEntity<?> findAudio(@PathVariable("question_id") Long questionId,
                                       @RequestHeader HttpHeaders headers){
        try{
            List<File> files = fileService.findByQuestionId(questionId);
            List<String> audioUrl = files.stream()
                    .filter(file -> file.getType() == FileType.Audio)
                    .map(file -> "file:" + file.getSavedFileName())
                    .toList();
            UrlResource resource = new UrlResource(audioUrl.get(0));
            ResourceRegion region = resourceRegion(resource, headers);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("video/mp4"))
                    .body(region);
        }catch (Exception e){
            ResponseDto response = ResponseDto.builder()
                    .status(ResponseStatus.FAIL)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    private ResourceRegion resourceRegion(UrlResource video, HttpHeaders headers) throws IOException {

        final long chunkSize = 1000000L;
        long contentLength = video.contentLength();

        HttpRange httpRange = headers.getRange().stream().findFirst().get();
        if(httpRange != null) {
            long start = httpRange.getRangeStart(contentLength);
            long end = httpRange.getRangeEnd(contentLength);
            long rangeLength = Long.min(chunkSize, end - start + 1);
            return new ResourceRegion(video, start, rangeLength);
        } else {
            long rangeLength = Long.min(chunkSize, contentLength);
            return new ResourceRegion(video, 0, rangeLength);
        }
    }
}
