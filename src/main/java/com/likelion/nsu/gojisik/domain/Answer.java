package com.likelion.nsu.gojisik.domain;

import com.likelion.nsu.gojisik.dto.AnswerRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    private String contents;

    private LocalDateTime createdDate;

    private Boolean isAdopted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public static Answer createAnswer(User user, Question question, AnswerRequestDto dto){
        Answer answer = AnswerRequestDto.toEntity(dto);
        answer.setUser(user);
        answer.setQuestion(question);
        return answer;
    }

    public void adopt(){
        this.isAdopted = Boolean.TRUE;
    }
}
