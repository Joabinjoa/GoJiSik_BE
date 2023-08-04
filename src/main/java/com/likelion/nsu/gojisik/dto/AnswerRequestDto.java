package com.likelion.nsu.gojisik.dto;

import com.likelion.nsu.gojisik.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class AnswerRequestDto {
    private String contents;

    public static Answer toEntity(AnswerRequestDto dto){
        return Answer.builder()
                .contents(dto.getContents())
                .createdDate(LocalDateTime.now())
                .isAdopted(Boolean.FALSE)
                .build();
    }
}
