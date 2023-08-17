package com.likelion.nsu.gojisik.repository;

import com.likelion.nsu.gojisik.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestion_Id(Long questionId);
    List<Answer> findByUser_Id(Long questionId);
}
