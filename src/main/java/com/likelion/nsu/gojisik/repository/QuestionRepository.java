package com.likelion.nsu.gojisik.repository;

import com.likelion.nsu.gojisik.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByUser_Id(Long userId);
    @Modifying
    @Query("update Question q set q.hits = q.hits + 1 where q.id = :id")
    int updateHits(@Param("id") Long id);
}
