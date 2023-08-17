package com.likelion.nsu.gojisik.dto;

import com.likelion.nsu.gojisik.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class QuestionResponseDto { //TODO - 감싸는거, 예외처리
    private Long id;
    private String title;
    private String category;
    private String contents;
    private LocalDateTime createdDate;
    private Long hits;
    private String writer;

    public QuestionResponseDto(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.category = question.getCategory();
        this.contents = question.getContents();
        this.createdDate = question.getCreatedDate();
        this.hits = question.getHits();
        this.writer = question.getUser().getUsername();
    }
}
