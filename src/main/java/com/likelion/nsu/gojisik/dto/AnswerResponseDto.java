package com.likelion.nsu.gojisik.dto;

import com.likelion.nsu.gojisik.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AnswerResponseDto {
    private Long id;
    private String contents;
    private LocalDateTime createdDate;
    private Boolean isAdopted;
    private String writer;

    public AnswerResponseDto(Answer answer){
        this.id = answer.getId();
        this.contents = answer.getContents();
        this.createdDate = answer.getCreatedDate();
        this.isAdopted = answer.getIsAdopted();
        this.writer = answer.getMember().getUsername();
    }
}
