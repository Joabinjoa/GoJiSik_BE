package com.likelion.nsu.gojisik.service;

import com.likelion.nsu.gojisik.domain.Member;
import com.likelion.nsu.gojisik.domain.Question;
import com.likelion.nsu.gojisik.dto.QuestionRequestDto;
import com.likelion.nsu.gojisik.repository.QuestionRepository;
import com.likelion.nsu.gojisik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService { //TODO - 테스트케이스 작성
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    // 질문 등록
    @Transactional
    public Long saveQuestion(Long userId, QuestionRequestDto dto) {
        Member user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));
        Question question = Question.createQuestion(user, dto);
        questionRepository.save(question);
        return question.getId();
    }

    // 질문 리스트 조회
    public List<Question> findQuestions() {
        return  questionRepository.findAll();
    }

    // 질문 조회
    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문입니다."));
    }

    // 질문 내역
    public List<Question> findByUserId(Long userId) {
        return questionRepository.findByMember_Id(userId);
    }
}

