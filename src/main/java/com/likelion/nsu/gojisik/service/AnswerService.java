package com.likelion.nsu.gojisik.service;

import com.likelion.nsu.gojisik.domain.Answer;
import com.likelion.nsu.gojisik.domain.Question;
import com.likelion.nsu.gojisik.domain.User;
import com.likelion.nsu.gojisik.dto.AnswerRequestDto;
import com.likelion.nsu.gojisik.repository.AnswerRepository;
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
public class AnswerService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public Long create(Long userId, Long questionId, AnswerRequestDto dto){
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문입니다."));
        Answer answer = Answer.createAnswer(user, question, dto);
        answerRepository.save(answer);
        return answer.getId();
    }

    @Transactional
    public Long adopt(Long answerId){
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 답변입니다."));
        answer.adopt();
        return answer.getId();
    }

    public List<Answer> findAnswers(Long questionId){
        return answerRepository.findByQuestion_Id(questionId);
    }

    public List<Answer> findByUserId(Long userId){
        return answerRepository.findByUser_Id(userId);
    }
}
