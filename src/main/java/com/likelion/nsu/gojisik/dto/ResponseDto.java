package com.likelion.nsu.gojisik.dto;

import com.likelion.nsu.gojisik.domain.Question;
import com.likelion.nsu.gojisik.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto { //TODO - 감싸는거, 예외처리

    private Long id;

    private String title;

    private String category;

    private String contents;

    private LocalDateTime createdDate;

    private Long hits;

    private User user;

    public Question toResponseEntity() {
        Question build = Question.builder()
                .id(id)
                .title(title)
                .category(category)
                .contents(contents)
                .createdDate(createdDate)
                .hits(hits)
                .user(user)
                .build();
        return build;
    }

    @Builder
    public ResponseDto(Long id, String title, String category, String contents, LocalDateTime createdDate, Long hits, User user) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.createdDate = createdDate;
        this.hits = hits;
        this.user = user;
    }
}
