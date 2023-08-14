package com.likelion.nsu.gojisik.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private FileType type;

    private String originFileName;

    private String savedFileName;

    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
}
