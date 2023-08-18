package com.likelion.nsu.gojisik.repository;

import com.likelion.nsu.gojisik.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByQuestion_Id(Long questionId);
}
