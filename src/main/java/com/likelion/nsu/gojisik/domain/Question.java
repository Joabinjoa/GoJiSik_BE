package com.likelion.nsu.gojisik.domain;

import com.likelion.nsu.gojisik.dto.QuestionRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String category;

    private String title;

    private String contents;

    private LocalDateTime createdDate;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Long hits;

    @OneToMany(mappedBy = "question")
    private List<File> files;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "question")
    private List<Answer> answerList;

    public static Question createQuestion(User user, QuestionRequestDto dto){
        Question question = QuestionRequestDto.toEntity(dto);
        question.setUser(user);
        return question;
    }
}
