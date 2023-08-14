package com.likelion.nsu.gojisik.service;

import com.likelion.nsu.gojisik.domain.File;
import com.likelion.nsu.gojisik.domain.Question;
import com.likelion.nsu.gojisik.handler.FileHandler;
import com.likelion.nsu.gojisik.repository.FileRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {
    private final EntityManager em;
    private final FileHandler fileHandler;
    private final FileRepository fileRepository;

    @Transactional
    public void saveFile(Question question, List<MultipartFile> multipartFiles) throws IOException {
        List<File> files = fileHandler.parseFile(question, multipartFiles);
        fileRepository.saveAll(files);
    }

    public List<File> findByQuestionId(Long questionId){
        return fileRepository.findByQuestion_Id(questionId);
    }
}
