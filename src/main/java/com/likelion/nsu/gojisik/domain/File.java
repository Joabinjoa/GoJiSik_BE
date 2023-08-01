package com.likelion.nsu.gojisik.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private FileType type;

    private String originFileName;

    private String savedFileName;

    private Long fileSize;

    @OneToOne(mappedBy = "file", fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
}
