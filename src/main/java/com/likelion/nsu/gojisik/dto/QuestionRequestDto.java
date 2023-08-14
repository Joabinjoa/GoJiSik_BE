package com.likelion.nsu.gojisik.dto;

import com.likelion.nsu.gojisik.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class QuestionRequestDto {
    private String category;
    private String title;
    private String contents;

    public static Question toEntity(QuestionRequestDto dto) {
        return Question.builder()
                .category(dto.getCategory())
                .title(dto.getTitle())
                .contents(dto.getContents())
                .createdDate(LocalDateTime.now())
                .hits(0L)
                .build();
    }
}
