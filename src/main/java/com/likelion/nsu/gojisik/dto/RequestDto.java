package com.likelion.nsu.gojisik.dto;

import com.likelion.nsu.gojisik.domain.File;
import com.likelion.nsu.gojisik.domain.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RequestDto {

    private String category;

    private String title;

    private String contents;

    public Question toRequestEntity() {
        Question build = Question.builder()
                .category(category)
                .title(title)
                .contents(contents)
                .build();
        return build;
    }

    @Builder
    public RequestDto(String category, String title, String contents) {
        this.category = category;
        this.title = title;
        this.contents = contents;
    }
}
