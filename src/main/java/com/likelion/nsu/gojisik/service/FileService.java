package com.likelion.nsu.gojisik.service;

import com.likelion.nsu.gojisik.domain.File;
import com.likelion.nsu.gojisik.domain.Question;
import com.likelion.nsu.gojisik.handler.FileHandler;
import com.likelion.nsu.gojisik.repository.FileRepository;
import com.likelion.nsu.gojisik.repository.QuestionRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {
    private final EntityManager em;
    private final FileHandler fileHandler;
    private final FileRepository fileRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void saveFile(Long questionId, List<MultipartFile> multipartFiles) throws IOException {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문입니다."));
        List<File> files = fileHandler.parseFile(question, multipartFiles);
        fileRepository.saveAll(files);
        log.info("file is saved");
    }

    public List<File> findByQuestionId(Long questionId){
        return fileRepository.findByQuestion_Id(questionId);
    }
}
