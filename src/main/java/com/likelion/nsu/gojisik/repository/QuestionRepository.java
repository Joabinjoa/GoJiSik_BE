package com.likelion.nsu.gojisik.repository;

import com.likelion.nsu.gojisik.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByUserId(Long userId);
}
